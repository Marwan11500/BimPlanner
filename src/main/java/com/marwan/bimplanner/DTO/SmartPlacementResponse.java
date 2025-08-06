package com.marwan.bimplanner.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmartPlacementResponse {
    private double suggestedX;
    private double suggestedY;
    private String reason;
}
