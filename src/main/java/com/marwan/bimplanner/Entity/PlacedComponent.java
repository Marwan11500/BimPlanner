package com.marwan.bimplanner.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "placed_components")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlacedComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float xCoord;
    private Float yCoord;
    private Float rotationDeg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floorplan_id")
    private FloorPlan floorPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_type_id")
    private ComponentType componentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    private LocalDateTime createdAt = LocalDateTime.now();
}
