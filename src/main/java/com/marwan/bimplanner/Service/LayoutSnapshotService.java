package com.marwan.bimplanner.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marwan.bimplanner.DTO.LayoutSnapshotRequest;
import com.marwan.bimplanner.DTO.LayoutSnapshotResponse;
import com.marwan.bimplanner.DTO.PlacedComponentRequest;
import com.marwan.bimplanner.DTO.PlacedComponentResponse;
import com.marwan.bimplanner.Entity.*;
import com.marwan.bimplanner.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LayoutSnapshotService {
    private final LayoutSnapshotRepository layoutSnapshotRepository;
    private final FloorPlanRepository floorPlanRepository;
    private final UserRepository userRepository;
    private final PlacedComponentRepository placedComponentRepository;
    private final ComponentTypeRepository ComponentTypeRepository;

    public LayoutSnapshotResponse createSnapshot(LayoutSnapshotRequest request) {
        FloorPlan floorPlan = floorPlanRepository.findById(request.getFloorPlanId())
                .orElseThrow(() -> new RuntimeException("Floor plan not found"));

        User creator = userRepository.findById(request.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found"));

        LayoutSnapshot snapshot = LayoutSnapshot.builder()
                .floorPlan(floorPlan)
                .jsonData(request.getJsonData())
                .snapshotJson(request.getSnapshotJson())
                .createdBy(creator)
                .createdAt(LocalDateTime.now())
                .build();

        LayoutSnapshot saved = layoutSnapshotRepository.save(snapshot);

        return LayoutSnapshotResponse.builder()
                .id(saved.getId())
                .floorPlanId(floorPlan.getId())
                .jsonData(saved.getJsonData())
                .snapshotJson(saved.getSnapshotJson())
                .createdBy(creator.getId())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    public LayoutSnapshotResponse saveSnapshot(LayoutSnapshotRequest request) {
        FloorPlan floorPlan = floorPlanRepository.findById(request.getFloorPlanId())
                .orElseThrow(() -> new RuntimeException("Floor plan not found"));

        User user = userRepository.findById(request.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found"));

        LayoutSnapshot snapshot = LayoutSnapshot.builder()
                .floorPlan(floorPlan)
                .jsonData(request.getJsonData())
                .snapshotJson(request.getSnapshotJson())
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .build();

        LayoutSnapshot saved = layoutSnapshotRepository.save(snapshot);

        return LayoutSnapshotResponse.builder()
                .id(saved.getId())
                .floorPlanId(saved.getFloorPlan().getId())
                .jsonData(saved.getJsonData())
                .snapshotJson(saved.getSnapshotJson())
                .createdBy(saved.getCreatedBy().getId())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    public List<LayoutSnapshotResponse> getSnapshotsByFloorPlan(Long floorPlanId) {
        FloorPlan floorPlan = floorPlanRepository.findById(floorPlanId)
                .orElseThrow(() -> new RuntimeException("Floor plan not found"));

        return layoutSnapshotRepository.findByFloorPlan(floorPlan)
                .stream()
                .map(snapshot -> LayoutSnapshotResponse.builder()
                        .id(snapshot.getId())
                        .floorPlanId(snapshot.getFloorPlan().getId())
                        .jsonData(snapshot.getJsonData())
                        .snapshotJson(snapshot.getSnapshotJson())
                        .createdBy(snapshot.getCreatedBy().getId())
                        .createdAt(snapshot.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<PlacedComponentResponse> restoreSnapshot(Long snapshotId) {
        // 0. Get the snapshot first
        LayoutSnapshot snapshot = layoutSnapshotRepository.findById(snapshotId)
                .orElseThrow(() -> new RuntimeException("Snapshot not found"));

        FloorPlan floorPlan = snapshot.getFloorPlan();

        // 1. Save current layout as a backup before restore
        try {
            List<PlacedComponent> currentComponents = placedComponentRepository.findByFloorPlan(floorPlan);

            List<PlacedComponentRequest> currentState = currentComponents.stream().map(pc ->
                    PlacedComponentRequest.builder()
                            .xCoord((double) pc.getXCoord())
                            .yCoord((double) pc.getYCoord())
                            .rotationDeg((double) pc.getRotationDeg())
                            .componentTypeId(pc.getComponentType().getId())
                            .createdBy(pc.getCreatedBy().getId())
                            .floorPlanId(pc.getFloorPlan().getId())
                            .build()
            ).toList();

            String currentLayoutJson = new ObjectMapper().writeValueAsString(currentState);

            LayoutSnapshot backup = LayoutSnapshot.builder()
                    .floorPlan(floorPlan)
                    .createdBy(snapshot.getCreatedBy()) // Now it's safe
                    .jsonData(currentLayoutJson)
                    .snapshotJson(null)
                    .createdAt(LocalDateTime.now())
                    .label("UndoBackup")
                    .build();

            layoutSnapshotRepository.save(backup);
        } catch (Exception e) {
            throw new RuntimeException("Could not save undo snapshot: " + e.getMessage());
        }

        // 2. Delete all existing components from the floor plan
        List<PlacedComponent> existing = placedComponentRepository.findByFloorPlan(floorPlan);
        placedComponentRepository.deleteAll(existing);

        // 3. Re-create components
        List<PlacedComponentResponse> restoredComponents = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<PlacedComponentRequest> components = objectMapper.readValue(
                    snapshot.getJsonData(),
                    new TypeReference<>() {}
            );

            for (PlacedComponentRequest request : components) {
                ComponentType type = ComponentTypeRepository.findById(request.getComponentTypeId())
                        .orElseThrow(() -> new RuntimeException("Component type not found"));

                User user = userRepository.findById(request.getCreatedBy())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                PlacedComponent newComponent = PlacedComponent.builder()
                        .xCoord((float) request.getXCoord())
                        .yCoord((float) request.getYCoord())
                        .rotationDeg((float) request.getRotationDeg())
                        .componentType(type)
                        .createdBy(user)
                        .floorPlan(floorPlan)
                        .build();

                PlacedComponent saved = placedComponentRepository.save(newComponent);

                restoredComponents.add(PlacedComponentResponse.builder()
                        .id(saved.getId())
                        .xCoord(saved.getXCoord())
                        .yCoord(saved.getYCoord())
                        .rotationDeg(saved.getRotationDeg())
                        .componentTypeId(saved.getComponentType().getId())
                        .componentName(saved.getComponentType().getName())
                        .floorPlanId(saved.getFloorPlan().getId())
                        .createdBy(saved.getCreatedBy().getId())
                        .build());
            }

            return restoredComponents;

        } catch (Exception e) {
            throw new RuntimeException("Failed to restore snapshot: " + e.getMessage());
        }
    }


    public List<PlacedComponentResponse> undoLastRestore(Long floorPlanId) {
        FloorPlan floorPlan = floorPlanRepository.findById(floorPlanId)
                .orElseThrow(() -> new RuntimeException("Floor plan not found"));

        List<LayoutSnapshot> backups = layoutSnapshotRepository.findByFloorPlan(floorPlan).stream()
                .filter(s -> "UndoBackup".equals(s.getLabel()))
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt())) // latest first
                .toList();

        if (backups.isEmpty()) {
            throw new RuntimeException("No undo backup available");
        }

        LayoutSnapshot lastBackup = backups.get(0);
        return restoreSnapshot(lastBackup.getId());
    }


}
