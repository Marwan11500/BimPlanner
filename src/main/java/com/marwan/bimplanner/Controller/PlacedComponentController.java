package com.marwan.bimplanner.Controller;

import com.marwan.bimplanner.DTO.PlacedComponentRequest;
import com.marwan.bimplanner.DTO.PlacedComponentResponse;
import com.marwan.bimplanner.Service.PlacedComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/placed-components")
@RequiredArgsConstructor
public class PlacedComponentController {

    private final PlacedComponentService placedComponentService;

    // POST /api/placed-components
    @PostMapping
    public ResponseEntity<PlacedComponentResponse> placeComponent(@RequestBody PlacedComponentRequest request) {
        System.out.println("Request: " + request);
        System.out.println("Received yCoord: " + request.getYCoord());
        PlacedComponentResponse response = placedComponentService.placeComponent(request);
        return ResponseEntity.ok(response);
    }

    // GET /api/placed-components/floorplan/{floorPlanId}
    @GetMapping("/floorplan/{floorPlanId}")
    public ResponseEntity<List<PlacedComponentResponse>> getByFloorPlan(@PathVariable Long floorPlanId) {
        List<PlacedComponentResponse> components = placedComponentService.getByFloorPlan(floorPlanId);
        return ResponseEntity.ok(components);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlacedComponentResponse> getById(@PathVariable Long id) {
        PlacedComponentResponse component = placedComponentService.getById(id);
        return ResponseEntity.ok(component);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlacedComponentResponse> updateComponent(
            @PathVariable Long id,
            @RequestBody PlacedComponentRequest request) {
        return ResponseEntity.ok(placedComponentService.updateComponent(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Long id) {
        placedComponentService.deleteComponent(id);
        return ResponseEntity.noContent().build();
    }


}
