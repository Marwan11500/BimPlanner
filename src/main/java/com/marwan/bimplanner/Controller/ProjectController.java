package com.marwan.bimplanner.Controller;

import com.marwan.bimplanner.DTO.ProjectRequest;
import com.marwan.bimplanner.DTO.ProjectResponse;
import com.marwan.bimplanner.Service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // POST /api/projects
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest request) {
        ProjectResponse created = projectService.create(request);
        return ResponseEntity.ok(created);
    }

    // GET /api/projects/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByUser(@PathVariable Long userId) {
        List<ProjectResponse> projects = projectService.getProjectsByUser(userId);
        return ResponseEntity.ok(projects);
    }
}
