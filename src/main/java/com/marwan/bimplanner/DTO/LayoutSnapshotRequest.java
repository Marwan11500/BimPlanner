package com.marwan.bimplanner.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LayoutSnapshotRequest {
    private Long floorPlanId;
    private Long createdBy;
    private String jsonData;
    private String snapshotJson;
}
