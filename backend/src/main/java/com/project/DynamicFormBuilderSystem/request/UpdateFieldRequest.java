package com.project.DynamicFormBuilderSystem.request;

import jakarta.validation.constraints.*;

import java.util.List;

public class UpdateFieldRequest {

    @NotEmpty
    private String label;

    @NotEmpty
    @Pattern(regexp = "text|number|date|color|select")
    private String type;

    @NotNull
    private Integer displayOrder;

    private Boolean required;

    private List<String> options;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
