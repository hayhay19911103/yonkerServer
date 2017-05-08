package com.exam.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created by mac on 2017/3/21.
 */

@Entity
public class IndustryStatus {

    @Id
    @GeneratedValue

    private Integer professionId;

    private String thumbImage;

    private String title;

    private String dateTime;

    @Transient
    private List<IndustryContent> contentList;

    public Integer getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
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

    public List<IndustryContent> getContentList() {
        return contentList;
    }

    public void setContentList(List<IndustryContent> contentList) {
        this.contentList = contentList;
    }
}
