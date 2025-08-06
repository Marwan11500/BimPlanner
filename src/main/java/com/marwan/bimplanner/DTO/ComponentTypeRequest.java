package com.marwan.bimplanner.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComponentTypeRequest {
    private String name;
    private Float width;
    private Float height;
    private String category;
    private String iconUrl;
}
