package com.project.DynamicFormBuilderSystem.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AnswerRequest {

    @NotNull
    @Min(1)
    private Long fieldId;

    private String value;

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
