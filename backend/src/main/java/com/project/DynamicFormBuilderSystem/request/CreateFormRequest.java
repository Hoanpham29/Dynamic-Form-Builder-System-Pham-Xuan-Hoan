package com.project.DynamicFormBuilderSystem.request;

import jakarta.validation.constraints.*;

import java.util.List;

public class CreateFormRequest {

    @NotEmpty(message = "Title is mandatory")
    @Size(min=3, max=200, message = "Title must be at least 3 characters long")
    private String title;

    @NotEmpty(message = "Description is mandatory")
    @Size(min=3, max=200, message = "Description must be at least 3 characters long")
    private String description;

    @Min(0)
    @Max(100)
    private Integer displayOrder;

    @NotEmpty
    @Pattern(regexp = "active|draft")
    private String status;

    public CreateFormRequest(String title, String description, Integer displayOrder, String status) {
        this.title = title;
        this.description = description;
        this.displayOrder = displayOrder;
        this.status = status;
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

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
