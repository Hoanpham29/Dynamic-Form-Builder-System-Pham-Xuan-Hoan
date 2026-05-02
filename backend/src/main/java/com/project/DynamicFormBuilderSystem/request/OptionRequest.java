package com.project.DynamicFormBuilderSystem.request;

public class OptionRequest {
    private String value;

    public OptionRequest(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
