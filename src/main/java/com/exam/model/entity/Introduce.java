package com.exam.model.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by mac on 2017/3/21.
 */
@Entity
public class Introduce {
    private Integer sectionId;
    private String imagePath;
    private String sectionText;

    @Id
    @GeneratedValue
    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    @Basic
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Basic

    public String getSectionText() {
        return sectionText;
    }
    public void setSectionText(String sectionText) {
        this.sectionText = sectionText;
    }

}
