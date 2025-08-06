package com.marwan.bimplanner.Repository;

import com.marwan.bimplanner.Entity.FloorPlan;
import com.marwan.bimplanner.Entity.PlacedComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlacedComponentRepository extends JpaRepository <PlacedComponent, Long>{
    List<PlacedComponent> findByFloorPlan(FloorPlan floorPlan);
}
