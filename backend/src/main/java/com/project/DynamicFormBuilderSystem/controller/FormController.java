package com.project.DynamicFormBuilderSystem.controller;

import com.project.DynamicFormBuilderSystem.request.CreateFormRequest;
import com.project.DynamicFormBuilderSystem.response.FormResponse;
import com.project.DynamicFormBuilderSystem.service.FormService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/forms")
@Tag(name = "Admin Form REST API Endpoints", description = "Operations for admin's managing use forms")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @Operation(summary = "Get all form", description = "Fetch all forms for admin preview")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<FormResponse> getAll(){
        return formService.getAll();
    }

    @Operation(summary = "Get a specific form", description = "Fetch one form for admin preview")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public FormResponse getFormById(@Min(1) @PathVariable Long id){
        return formService.findFormById(id);
    }

    @Operation(summary = "Create a new form", description = "Create new form for employee")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public FormResponse createForm(@Valid @RequestBody CreateFormRequest createFormRequest){
        return formService.createNewForm(createFormRequest);
    }
}
