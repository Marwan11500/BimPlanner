package com.marwan.bimplanner.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LayoutSnapshotResponse {
    private Long id;
    private Long floorPlanId;
    private Long createdBy;
    private String jsonData;
    private String snapshotJson;
    private LocalDateTime createdAt;
}
