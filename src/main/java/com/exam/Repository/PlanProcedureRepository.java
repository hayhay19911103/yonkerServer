package com.exam.Repository;

import com.exam.model.entity.PlanProcedure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mac on 2017/3/2.
 */
public interface PlanProcedureRepository extends JpaRepository<PlanProcedure,Integer> {
    public PlanProcedure findByProcedureName(String procedureName);
    public List<PlanProcedure> findByPlanId(Integer planId);
    public PlanProcedure findByPlanIdAndProcedureName(Integer planId, String procedureName);
    public List<PlanProcedure> findByProcedureNameLike(String procedureName);
    public PlanProcedure findByProcedureId(Integer procedureId);


}
