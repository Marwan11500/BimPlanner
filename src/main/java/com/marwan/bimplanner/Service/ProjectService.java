package com.marwan.bimplanner.Service;

import com.marwan.bimplanner.DTO.ProjectRequest;
import com.marwan.bimplanner.DTO.ProjectResponse;
import com.marwan.bimplanner.Entity.Project;
import com.marwan.bimplanner.Entity.User;
import com.marwan.bimplanner.Repository.ProjectRepository;
import com.marwan.bimplanner.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectResponse create(ProjectRequest request) {
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .owner(owner)
                .build();

        Project saved = projectRepository.save(project);

        return ProjectResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .description(saved.getDescription())
                .ownerId(owner.getId())
                .ownerName(owner.getName())
                .build();
    }

    public List<ProjectResponse> getProjectsByUser(Long userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return projectRepository.findByOwner(owner).stream()
                .map(p -> ProjectResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .description(p.getDescription())
                        .ownerId(p.getOwner().getId())
                        .ownerName(p.getOwner().getName())
                        .build()
                ).collect(Collectors.toList());
    }
}
