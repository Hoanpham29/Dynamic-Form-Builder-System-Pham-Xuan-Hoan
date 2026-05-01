package com.project.DynamicFormBuilderSystem.request;

import jakarta.validation.constraints.*;

import java.util.List;

public class CreateFieldRequest {

    @NotEmpty(message = "Label is mandatory")
    @Size(min=3, max=200, message = "Label must be at least 3 characters long")
    private String label;

    @NotEmpty(message = "Type is mandatory")
    @Pattern(regexp = "text|number|date|color|select")
    private String type;

    @Min(0)
    @Max(100)
    private Integer displayOrder;

    private Boolean required;

    private List<String> options;

    public CreateFieldRequest(String label, String type, Integer displayOrder, Boolean required, List<String> options) {
        this.label = label;
        this.type = type;
        this.displayOrder = displayOrder;
        this.required = required;
        this.options = options;
    }

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
