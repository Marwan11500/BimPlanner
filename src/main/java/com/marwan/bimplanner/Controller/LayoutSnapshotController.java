package com.marwan.bimplanner.Controller;

import com.marwan.bimplanner.DTO.LayoutSnapshotRequest;
import com.marwan.bimplanner.DTO.LayoutSnapshotResponse;
import com.marwan.bimplanner.DTO.PlacedComponentResponse;
import com.marwan.bimplanner.Service.LayoutSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/layout-snapshots")
@RequiredArgsConstructor
public class LayoutSnapshotController {

    private final LayoutSnapshotService layoutSnapshotService;

    // Create a new layout snapshot
    @PostMapping
    public ResponseEntity<LayoutSnapshotResponse> createSnapshot(@RequestBody LayoutSnapshotRequest request) {
        LayoutSnapshotResponse response = layoutSnapshotService.createSnapshot(request);
        return ResponseEntity.ok(response);
    }

    // Get all snapshots for a specific floor plan
    @GetMapping("/floorplan/{floorPlanId}")
    public ResponseEntity<List<LayoutSnapshotResponse>> getSnapshotsByFloorPlan(@PathVariable Long floorPlanId) {
        List<LayoutSnapshotResponse> responses = layoutSnapshotService.getSnapshotsByFloorPlan(floorPlanId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/restore/{snapshotId}")
    public ResponseEntity<List<PlacedComponentResponse>> restoreSnapshot(@PathVariable Long snapshotId) {
        List<PlacedComponentResponse> restored = layoutSnapshotService.restoreSnapshot(snapshotId);
        return ResponseEntity.ok(restored);
    }

    @GetMapping("/undo/{floorPlanId}")
    public ResponseEntity<List<PlacedComponentResponse>> undoLastRestore(@PathVariable Long floorPlanId) {
        return ResponseEntity.ok(layoutSnapshotService.undoLastRestore(floorPlanId));
    }

}
