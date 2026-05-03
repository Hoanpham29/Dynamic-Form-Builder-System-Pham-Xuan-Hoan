package com.project.DynamicFormBuilderSystem.controller;

import com.project.DynamicFormBuilderSystem.request.CreateFieldRequest;
import com.project.DynamicFormBuilderSystem.request.UpdateFieldRequest;
import com.project.DynamicFormBuilderSystem.response.FieldResponse;
import com.project.DynamicFormBuilderSystem.service.FieldService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forms/{formId}/fields")
@Tag(name = "Admin Field REST API Endpoints", description = "Operations for admin's managing use fields")
public class FieldController {

    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @Operation(summary = "Create field for form", description = "Create field for a single form")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public FieldResponse createField(@PathVariable Long formId,
                                     @Valid @RequestBody CreateFieldRequest createFieldRequest){
        return fieldService.createNewField(formId, createFieldRequest);
    }

    @Operation(summary = "Update field for form", description = "Update field in specific form")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public FieldResponse updateField(@PathVariable Long formId,
                                     @Valid @RequestBody UpdateFieldRequest updateFieldRequest,
                                     @PathVariable @Min(1) Long id){
        return fieldService.updateField(formId, id,updateFieldRequest);
    }

    @Operation(summary = "Delete field in form", description = "Delete field in specific form")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteField(@PathVariable Long formId,
                            @PathVariable @Min(1) Long id){
        fieldService.deleteField(formId, id);
    }
}
