package com.project.DynamicFormBuilderSystem.service;

import com.project.DynamicFormBuilderSystem.request.CreateFieldRequest;
import com.project.DynamicFormBuilderSystem.request.UpdateFieldRequest;
import com.project.DynamicFormBuilderSystem.response.FieldResponse;

public interface FieldService {
    FieldResponse createNewField(Long formId, CreateFieldRequest createFieldRequest);
    FieldResponse updateField(Long formId, Long id, UpdateFieldRequest updateFieldRequest);
    void deleteField(Long formId, Long id);
}
