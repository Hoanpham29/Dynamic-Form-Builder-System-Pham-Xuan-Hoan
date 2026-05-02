package com.project.DynamicFormBuilderSystem.response;

import jakarta.validation.constraints.NotNull;

public class AnswerResponse {
    @NotNull
    private Long fieldId;

    @NotNull
    private String label;

    @NotNull
    private String type;

    @NotNull
    private String value;

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
