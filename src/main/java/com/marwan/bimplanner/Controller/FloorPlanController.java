package com.marwan.bimplanner.Controller;

import com.marwan.bimplanner.DTO.FloorPlanRequest;
import com.marwan.bimplanner.DTO.FloorPlanResponse;
import com.marwan.bimplanner.Service.FloorPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/floorplans")
@RequiredArgsConstructor
public class FloorPlanController {

    private final FloorPlanService floorPlanService;

    // POST /api/floorplans
    @PostMapping
    public ResponseEntity<FloorPlanResponse> createFloorPlan(@RequestBody FloorPlanRequest request) {
        FloorPlanResponse response = floorPlanService.create(request);
        return ResponseEntity.ok(response);
    }

    // GET /api/floorplans/project/{projectId}
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<FloorPlanResponse>> getByProject(@PathVariable Long projectId) {
        List<FloorPlanResponse> plans = floorPlanService.getByProject(projectId);
        return ResponseEntity.ok(plans);
    }
}
