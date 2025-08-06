package com.marwan.bimplanner.Repository;

import com.marwan.bimplanner.Entity.FloorPlan;
import com.marwan.bimplanner.Entity.LayoutSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LayoutSnapshotRepository extends JpaRepository<LayoutSnapshot, Long> {
    List<LayoutSnapshot> findByFloorPlan(FloorPlan floorPlan);
}
