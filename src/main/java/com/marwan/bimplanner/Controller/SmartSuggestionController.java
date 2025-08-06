package com.marwan.bimplanner.Controller;

import com.marwan.bimplanner.DTO.SmartPlacementRequest;
import com.marwan.bimplanner.DTO.SmartPlacementResponse;
import com.marwan.bimplanner.Service.SmartSuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/suggestions")
@RequiredArgsConstructor
public class SmartSuggestionController {

    private final SmartSuggestionService smartSuggestionService;

    @PostMapping("/placement")
    public ResponseEntity<SmartPlacementResponse> suggestPlacement(@RequestBody SmartPlacementRequest request) {
        SmartPlacementResponse response = smartSuggestionService.suggestPlacement(request);
        return ResponseEntity.ok(response);
    }
}
