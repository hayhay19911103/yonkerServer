package com.exam.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by mac on 2017/3/22.
 */

@Entity
public class RepairContent {

    private Integer contentId;
    private Integer situationId;
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

    public Integer getSituationId() {
        return situationId;
    }

    public void setSituationId(Integer situationId) {
        this.situationId = situationId;
    }

    public Integer getParaGraph() {
        return paraGraph;
    }

    public void setParaGraph(Integer paraGraph) {
        this.paraGraph = paraGraph;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
