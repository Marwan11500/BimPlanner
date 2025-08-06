package com.marwan.bimplanner.Service;

import com.marwan.bimplanner.DTO.FloorPlanRequest;
import com.marwan.bimplanner.DTO.FloorPlanResponse;
import com.marwan.bimplanner.Entity.FloorPlan;
import com.marwan.bimplanner.Entity.Project;
import com.marwan.bimplanner.Repository.FloorPlanRepository;
import com.marwan.bimplanner.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FloorPlanService {

    private final FloorPlanRepository floorPlanRepository;
    private final ProjectRepository projectRepository;

    public FloorPlanResponse create(FloorPlanRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        FloorPlan plan = FloorPlan.builder()
                .name(request.getName())
                .fileUrl(request.getFileUrl())
                .widthMeters(request.getWidthMeters())
                .heightMeters(request.getHeightMeters())
                .gridUnit(request.getGridUnit())
                .project(project)
                .build();

        FloorPlan saved = floorPlanRepository.save(plan);

        return FloorPlanResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .fileUrl(saved.getFileUrl())
                .widthMeters(saved.getWidthMeters())
                .heightMeters(saved.getHeightMeters())
                .gridUnit(saved.getGridUnit())
                .projectId(project.getId())
                .build();
    }

    public List<FloorPlanResponse> getByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return floorPlanRepository.findByProject(project).stream()
                .map(fp -> FloorPlanResponse.builder()
                        .id(fp.getId())
                        .name(fp.getName())
                        .fileUrl(fp.getFileUrl())
                        .widthMeters(fp.getWidthMeters())
                        .heightMeters(fp.getHeightMeters())
                        .gridUnit(fp.getGridUnit())
                        .projectId(project.getId())
                        .build())
                .collect(Collectors.toList());
    }
}
