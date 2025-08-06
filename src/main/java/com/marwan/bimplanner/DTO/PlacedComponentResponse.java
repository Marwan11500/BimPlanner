package com.marwan.bimplanner.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlacedComponentResponse {
    private Long id;

    @JsonProperty("xCoord")
    private Float xCoord;

    @JsonProperty("yCoord")
    private Float yCoord;

    @JsonProperty("rotationDeg")
    private Float rotationDeg;

    @JsonProperty("floorPlanId")
    private Long floorPlanId;

    @JsonProperty("componentTypeId")
    private Long componentTypeId;

    @JsonProperty("componentName")
    private String componentName;

    @JsonProperty("createdBy")
    private Long createdBy;
}
