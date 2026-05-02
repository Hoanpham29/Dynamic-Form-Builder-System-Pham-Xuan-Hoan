package com.project.DynamicFormBuilderSystem.service;

import com.project.DynamicFormBuilderSystem.request.CreateFormRequest;
import com.project.DynamicFormBuilderSystem.request.UpdateFormRequest;
import com.project.DynamicFormBuilderSystem.response.FormResponse;

import java.util.List;

public interface FormService {
    List<FormResponse> getAll();
    FormResponse createNewForm(CreateFormRequest createFormRequest);
    FormResponse findFormById(long id);
    FormResponse updateForm(long id, UpdateFormRequest request);
    void deleteForm(long id);
}
