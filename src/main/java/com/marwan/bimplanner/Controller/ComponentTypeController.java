package com.marwan.bimplanner.Controller;

import com.marwan.bimplanner.DTO.ComponentTypeRequest;
import com.marwan.bimplanner.DTO.ComponentTypeResponse;
import com.marwan.bimplanner.Service.ComponentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/component-types")
@RequiredArgsConstructor
public class ComponentTypeController {

    private final ComponentTypeService componentTypeService;

    // POST /api/component-types
    @PostMapping
    public ResponseEntity<ComponentTypeResponse> create(@RequestBody ComponentTypeRequest request) {
        return ResponseEntity.ok(componentTypeService.create(request));
    }

    // GET /api/component-types
    @GetMapping
    public ResponseEntity<List<ComponentTypeResponse>> getAll() {
        return ResponseEntity.ok(componentTypeService.getAll());
    }

    public ResponseEntity<ComponentTypeResponse> getComponentDetails(@PathVariable Long id) {
        return ResponseEntity.ok(componentTypeService.getComponentDetails(id));
    }
}
