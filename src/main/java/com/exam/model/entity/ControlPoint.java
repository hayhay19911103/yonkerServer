package com.exam.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by dell-ewtu on 2017/3/8.
 */
@Entity
public class ControlPoint {
    @Id
    @GeneratedValue
    private Integer controlPointId;
    private Integer procedureId;
    private String controlPointName;

    private String procedureName;


    public Integer getControlPointId() {
        return controlPointId;
    }

    public void setControlPointId(Integer controlPointId) {
        this.controlPointId = controlPointId;
    }

    public Integer getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Integer procedureId) {
        this.procedureId = procedureId;
    }

    public String getControlPointName() {
        return controlPointName;
    }

    public void setControlPointName(String controlPointName) {
        this.controlPointName = controlPointName;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }
}
