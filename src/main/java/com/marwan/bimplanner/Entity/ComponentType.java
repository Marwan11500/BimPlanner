package com.marwan.bimplanner.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "component_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComponentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Float width;
    private Float height;
    private String category;
    private String iconUrl;
    private String material;
    private String size;
    private Double cost;
}
