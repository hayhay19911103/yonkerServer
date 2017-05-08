package com.exam.model.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by mac on 2017/4/28.
 */
@Entity
public class IndustryContent {

    private Integer contentId;
    private Integer professionId;
    private Integer paraGraph;
    private String image;
    private String content;

    @Id
    @GeneratedValue
    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    @Basic
    public Integer getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
    }

    @Basic
    public Integer getParaGraph() {
        return paraGraph;
    }

    public void setParaGraph(Integer paraGraph) {
        this.paraGraph = paraGraph;
    }

    @Basic
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
