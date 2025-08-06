package com.marwan.bimplanner.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FloorPlanResponse {
    private Long id;
    private String name;
    private String fileUrl;
    private Long projectId;
    private Float widthMeters;
    private Float heightMeters;
    private Float gridUnit;
}
