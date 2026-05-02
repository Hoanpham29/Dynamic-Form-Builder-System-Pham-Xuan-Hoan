package com.project.DynamicFormBuilderSystem.controller;

import com.project.DynamicFormBuilderSystem.request.SubmissionRequest;
import com.project.DynamicFormBuilderSystem.response.FormResponse;
import com.project.DynamicFormBuilderSystem.response.SubmissionResponse;
import com.project.DynamicFormBuilderSystem.service.FormService;
import com.project.DynamicFormBuilderSystem.service.SubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Submission REST API Endpoints", description = "Operations for employee preview")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @Operation(summary = "Get all form", description = "Fetch all employee's submitted forms")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/submissions")
    public List<SubmissionResponse> getSubmissions(){
        return submissionService.getSubmissions();
    }

    @Operation(summary = "Submit form", description = "Submit form confirmation")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/forms/{id}/submit")
    public void submitForm(@Min(1) @PathVariable Long id,
                           @RequestBody SubmissionRequest submissionRequest){
        submissionService.submitForm(id, submissionRequest);
    }

    @Operation(summary = "Get all active forms", description = "Fetch all active forms to submit")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/forms/active")
    public List<FormResponse> getActiveForms(){
        return submissionService.getActiveForms();
    }
}
