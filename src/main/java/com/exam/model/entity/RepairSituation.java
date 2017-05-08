package com.exam.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by mac on 2017/3/22.
 */
@Entity
public class RepairSituation {

    @Id
    @GeneratedValue

    private Integer situationId;

    private String thumbImage;

    private String title;

    private String dateTime;


    public Integer getSituationId() {
        return situationId;
    }

    public void setSituationId(Integer situationId) {
        this.situationId = situationId;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
