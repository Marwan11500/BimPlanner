package com.marwan.bimplanner.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FloorPlanRequest {
    private String name;
    private String fileUrl;
    private Long projectId;
    private Float widthMeters;
    private Float heightMeters;
    private Float gridUnit;
}
