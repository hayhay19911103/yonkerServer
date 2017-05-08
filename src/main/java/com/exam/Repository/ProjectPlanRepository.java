package com.exam.Repository;

import com.exam.model.entity.ProjectPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mac on 2017/3/8.
 */
public interface ProjectPlanRepository extends JpaRepository<ProjectPlan,Integer> {
    public List<ProjectPlan> findByProjectId(Integer projectId);

}
