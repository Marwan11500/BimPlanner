package com.marwan.bimplanner.Service;

import com.marwan.bimplanner.DTO.ComponentTypeRequest;
import com.marwan.bimplanner.DTO.ComponentTypeResponse;
import com.marwan.bimplanner.Entity.ComponentType;
import com.marwan.bimplanner.Repository.ComponentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComponentTypeService {

    private final ComponentTypeRepository componentTypeRepository;

    public ComponentTypeResponse create(ComponentTypeRequest request) {
        ComponentType type = ComponentType.builder()
                .name(request.getName())
                .width(request.getWidth())
                .height(request.getHeight())
                .iconUrl(request.getIconUrl())
                .build();

        ComponentType saved = componentTypeRepository.save(type);

        return ComponentTypeResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .width(saved.getWidth())
                .height(saved.getHeight())
                .category(saved.getCategory())
                .iconUrl(saved.getIconUrl())
                .build();
    }

    public List<ComponentTypeResponse> getAll() {
        return componentTypeRepository.findAll().stream()
                .map(type -> ComponentTypeResponse.builder()
                        .id(type.getId())
                        .name(type.getName())
                        .width(type.getWidth())
                        .height(type.getHeight())
                        .category(type.getCategory())
                        .iconUrl(type.getIconUrl())
                        .build())
                .collect(Collectors.toList());
    }

    public ComponentTypeResponse getComponentDetails(Long id) {
        ComponentType type = componentTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Component Type not found"));

        return ComponentTypeResponse.builder()
                .id(type.getId())
                .name(type.getName())
                .material(type.getMaterial())
                .size(type.getSize())
                .cost(type.getCost())
                .build();
    }
}
