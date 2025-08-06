package com.marwan.bimplanner.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlacedComponentRequest {
    @JsonProperty("xCoord")
    private double xCoord;

    @JsonProperty("yCoord")
    private double yCoord;

    @JsonProperty("rotationDeg")
    private double rotationDeg;

    @JsonProperty("floorPlanId")
    private Long floorPlanId;

    @JsonProperty("componentTypeId")
    private Long componentTypeId;

    @JsonProperty("createdBy")
    private Long createdBy;

}
