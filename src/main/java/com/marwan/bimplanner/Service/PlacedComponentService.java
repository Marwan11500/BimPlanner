package com.marwan.bimplanner.Service;

import com.marwan.bimplanner.DTO.PlacedComponentRequest;
import com.marwan.bimplanner.DTO.PlacedComponentResponse;
import com.marwan.bimplanner.Entity.ComponentType;
import com.marwan.bimplanner.Entity.FloorPlan;
import com.marwan.bimplanner.Entity.PlacedComponent;
import com.marwan.bimplanner.Entity.User;
import com.marwan.bimplanner.Repository.ComponentTypeRepository;
import com.marwan.bimplanner.Repository.FloorPlanRepository;
import com.marwan.bimplanner.Repository.PlacedComponentRepository;
import com.marwan.bimplanner.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlacedComponentService {

    private final PlacedComponentRepository placedComponentRepository;
    private final ComponentTypeRepository componentTypeRepository;
    private final FloorPlanRepository floorPlanRepository;
    private final UserRepository userRepository;

    private double snapToGrid(double value, double gridSize) {
        return Math.ceil(value / gridSize) * gridSize;
    }

    public PlacedComponentResponse placeComponent(PlacedComponentRequest request) {
        FloorPlan floorPlan = floorPlanRepository.findById(request.getFloorPlanId())
                .orElseThrow(() -> new RuntimeException("Floor plan not found"));

        ComponentType type = componentTypeRepository.findById(request.getComponentTypeId())
                .orElseThrow(() -> new RuntimeException("Component type not found"));

        User creator = userRepository.findById(request.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found"));


        double gridSize = 50.0;

        float snappedX = (float) snapToGrid(request.getXCoord(), gridSize);
        float snappedY = (float) snapToGrid(request.getYCoord(), gridSize);

        System.out.println("Original X: " + request.getXCoord());
        System.out.println("Original Y: " + request.getYCoord());
        System.out.println("Snapped X: " + snappedX);
        System.out.println("Snapped Y: " + snappedY);

        PlacedComponent component = PlacedComponent.builder()
                .xCoord(snappedX)
                .yCoord(snappedY)
                .rotationDeg((float) request.getRotationDeg())
                .floorPlan(floorPlan)
                .componentType(type)
                .createdBy(creator)
                .build();

        PlacedComponent saved = placedComponentRepository.save(component);

        return PlacedComponentResponse.builder()
                .id(saved.getId())
                .xCoord(saved.getXCoord())
                .yCoord(saved.getYCoord())
                .rotationDeg(saved.getRotationDeg())
                .componentName(type.getName())
                .componentTypeId(type.getId())
                .floorPlanId(floorPlan.getId())
                .createdBy(creator.getId())
                .build();
    }

    public List<PlacedComponentResponse> getByFloorPlan(Long floorPlanId) {
        FloorPlan floorPlan = floorPlanRepository.findById(floorPlanId)
                .orElseThrow(() -> new RuntimeException("Floor plan not found"));

        return placedComponentRepository.findByFloorPlan(floorPlan).stream()
                .map(pc -> PlacedComponentResponse.builder()
                        .id(pc.getId())
                        .xCoord(pc.getXCoord())
                        .yCoord(pc.getYCoord())
                        .rotationDeg(pc.getRotationDeg())
                        .componentName(pc.getComponentType().getName())
                        .componentTypeId(pc.getComponentType().getId())
                        .floorPlanId(floorPlan.getId())
                        .createdBy(pc.getCreatedBy().getId())
                        .build())
                .collect(Collectors.toList());
    }

    public PlacedComponentResponse getById(Long id) {
        PlacedComponent component = placedComponentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Placed component not found"));

        return PlacedComponentResponse.builder()
                .id(component.getId())
                .xCoord(component.getXCoord())
                .yCoord(component.getYCoord())
                .rotationDeg(component.getRotationDeg())
                .componentName(component.getComponentType().getName())
                .componentTypeId(component.getComponentType().getId())
                .floorPlanId(component.getFloorPlan().getId())
                .createdBy(component.getCreatedBy().getId())
                .build();
    }


    public PlacedComponentResponse updateComponent(Long id, PlacedComponentRequest request) {
        PlacedComponent component = placedComponentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Component not found"));

        FloorPlan floorPlan = floorPlanRepository.findById(request.getFloorPlanId())
                .orElseThrow(() -> new RuntimeException("Floor plan not found"));

        ComponentType type = componentTypeRepository.findById(request.getComponentTypeId())
                .orElseThrow(() -> new RuntimeException("Component type not found"));

        User creator = userRepository.findById(request.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found"));

        component.setXCoord((float) request.getXCoord());
        component.setYCoord((float) request.getYCoord());
        component.setRotationDeg((float) request.getRotationDeg());
        component.setFloorPlan(floorPlan);
        component.setComponentType(type);
        component.setCreatedBy(creator);

        PlacedComponent updated = placedComponentRepository.save(component);

        return PlacedComponentResponse.builder()
                .id(updated.getId())
                .xCoord(updated.getXCoord())
                .yCoord(updated.getYCoord())
                .rotationDeg(updated.getRotationDeg())
                .componentTypeId(updated.getComponentType().getId())
                .componentName(updated.getComponentType().getName())
                .floorPlanId(updated.getFloorPlan().getId())
                .createdBy(updated.getCreatedBy().getId())
                .build();
    }

    public void deleteComponent(Long id) {
        if (!placedComponentRepository.existsById(id)) {
            throw new RuntimeException("Component not found");
        }
        placedComponentRepository.deleteById(id);
    }


}
