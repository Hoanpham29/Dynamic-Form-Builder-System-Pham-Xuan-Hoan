package com.project.DynamicFormBuilderSystem.mapper;

import com.project.DynamicFormBuilderSystem.entity.Form;
import com.project.DynamicFormBuilderSystem.response.FieldResponse;
import com.project.DynamicFormBuilderSystem.response.FormResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FormMapper {
    public FormResponse toResponse(Form form, List<FieldResponse> fields) {

        FormResponse res = new FormResponse();
        res.setId(form.getId());
        res.setTitle(form.getTitle());
        res.setDescription(form.getDescription());
        res.setStatus(form.getStatus());
        res.setDisplayOrder(form.getDisplayOrder());

        res.setFields(fields);

        return res;
    }
}
