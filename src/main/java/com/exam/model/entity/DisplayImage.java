package com.exam.model.entity;

import javax.persistence.*;

/**
 * Created by dell-ewtu on 2017/3/16.
 */
@Entity
public class DisplayImage {
    @Id
    @GeneratedValue
    @Basic
    @Column(name = "displayImageId")
    private Integer displayImageId;

    @Basic
    @Column(name = "displayImagePath")
    private String displayImagePath;

    @Basic
    @Column(name = "displayImageName")
    private String displayImageName;

    public int getDisplayImageId() {
        return displayImageId;
    }

    public void setDisplayImageId(int displayImageId) {
        this.displayImageId = displayImageId;
    }

    public void setDisplayImageId(Integer displayImageId) {
        this.displayImageId = displayImageId;
    }

    public String getDisplayImagePath() {
        return displayImagePath;
    }

    public void setDisplayImagePath(String displayImagePath) {
        this.displayImagePath = displayImagePath;
    }

    public String getDisplayImageName() {
        return displayImageName;
    }

    public void setDisplayImageName(String displayImageName) {
        this.displayImageName = displayImageName;
    }
}
