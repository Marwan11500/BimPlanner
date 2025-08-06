package com.marwan.bimplanner.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComponentTypeResponse {
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
