package com.marwan.bimplanner.Service;

import com.marwan.bimplanner.DTO.SmartPlacementRequest;
import com.marwan.bimplanner.DTO.SmartPlacementResponse;
import com.marwan.bimplanner.Entity.FloorPlan;
import com.marwan.bimplanner.Entity.PlacedComponent;
import com.marwan.bimplanner.Repository.FloorPlanRepository;
import com.marwan.bimplanner.Repository.PlacedComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SmartSuggestionService {

    private final FloorPlanRepository floorPlanRepository;
    private final PlacedComponentRepository placedComponentRepository;

    public SmartPlacementResponse suggestPlacement(SmartPlacementRequest request) {
        FloorPlan floorPlan = floorPlanRepository.findById(request.getFloorPlanId())
                .orElseThrow(() -> new RuntimeException("Floor plan not found"));

        List<PlacedComponent> existingComponents =
                placedComponentRepository.findByFloorPlan(floorPlan);

        // 🧠 Basic strategy: find the right-most bottom-most position and suggest a small offset
        float suggestedX = 50;
        float suggestedY = 50;

        if (!existingComponents.isEmpty()) {
            PlacedComponent last = existingComponents.stream()
                    .max(Comparator.comparingDouble(c -> c.getXCoord() + c.getYCoord()))
                    .orElse(null);

            if (last != null) {
                suggestedX = last.getXCoord() + 100; // offset by 100 units to the right
                suggestedY = last.getYCoord();
            }
        }

        return SmartPlacementResponse.builder()
                .suggestedX(suggestedX)
                .suggestedY(suggestedY)
                .reason("Placed next to existing components to avoid overlap")
                .build();
    }
}
