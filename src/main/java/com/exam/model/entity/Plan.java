package com.exam.model.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by mac on 2017/3/2.
 */
@Entity
public class Plan {
    @Id
    @GeneratedValue

    @Column(length = 11)
    Integer planId;

    @Column(length = 50)
    String planName;

    @Transient
    private List<PlanProcedure> planProcedureList;



    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public List<PlanProcedure> getPlanProcedureList() {
        return planProcedureList;
    }

    public void setPlanProcedureList(List<PlanProcedure> planProcedureList) {
        this.planProcedureList = planProcedureList;
    }
}