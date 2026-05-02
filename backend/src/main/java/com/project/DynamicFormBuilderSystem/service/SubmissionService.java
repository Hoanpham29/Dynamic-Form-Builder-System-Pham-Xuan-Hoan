package com.project.DynamicFormBuilderSystem.service;

import com.project.DynamicFormBuilderSystem.request.SubmissionRequest;
import com.project.DynamicFormBuilderSystem.response.FormResponse;
import com.project.DynamicFormBuilderSystem.response.SubmissionResponse;

import java.util.List;

public interface SubmissionService {

    List<SubmissionResponse> getSubmissions();
    List<FormResponse> getActiveForms();
    void submitForm(Long id, SubmissionRequest submissionRequest);
}
