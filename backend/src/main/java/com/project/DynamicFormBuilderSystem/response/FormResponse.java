package com.project.DynamicFormBuilderSystem.response;

import java.util.List;

public class FormResponse {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Integer displayOrder;

    private List<FieldResponse> fields;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public List<FieldResponse> getFields() {
        return fields;
    }

    public void setFields(List<FieldResponse> fields) {
        this.fields = fields;
    }
}
