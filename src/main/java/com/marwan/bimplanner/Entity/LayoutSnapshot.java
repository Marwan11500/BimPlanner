package com.marwan.bimplanner.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "layout_snapshots")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LayoutSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "floorplan_id", nullable = false)
    private FloorPlan floorPlan;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Lob
    @Column(name = "json_data", nullable = false, columnDefinition = "TEXT")
    private String jsonData;

    @Column(name = "snapshot_json", columnDefinition = "TEXT", nullable = true)
    private String snapshotJson;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "label")
    private String label;
}
