package com.exam.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by dell-ewtu on 2017/3/9.
 */
@Entity
public class Collect {
    @Id
    @GeneratedValue
    private Integer collectionId;
    private int controlPointId;
    private String collectionName;
    private String collectionWeight;

    private Integer step;
    private Integer proStep;
    private Integer nextStep;



    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public int getControlPointId() {
        return controlPointId;
    }

    public void setControlPointId(int controlPointId) {
        this.controlPointId = controlPointId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionWeight() {
        return collectionWeight;
    }

    public void setCollectionWeight(String collectionWeight) {
        this.collectionWeight = collectionWeight;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getProStep() {
        return proStep;
    }

    public void setProStep(Integer proStep) {
        this.proStep = proStep;
    }

    public Integer getNextStep() {
        return nextStep;
    }

    public void setNextStep(Integer nextStep) {
        this.nextStep = nextStep;
    }
}
