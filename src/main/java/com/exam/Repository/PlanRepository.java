package com.exam.Repository;


import com.exam.model.entity.Plan;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mac on 2017/3/2.
 */
public interface PlanRepository extends JpaRepository<Plan,Integer> {
    public Plan findByPlanName(String planName);
    public Plan findByPlanId(Integer planId);
    public List<Plan> findByPlanNameLike(String planName, Sort sort);
}
