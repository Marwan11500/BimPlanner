package com.marwan.bimplanner.Repository;

import com.marwan.bimplanner.Entity.FloorPlan;
import com.marwan.bimplanner.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FloorPlanRepository extends JpaRepository<FloorPlan, Long> {
    List<FloorPlan> findByProject(Project project);
}
