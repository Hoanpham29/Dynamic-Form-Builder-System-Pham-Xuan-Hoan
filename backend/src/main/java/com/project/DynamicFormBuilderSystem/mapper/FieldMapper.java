package com.project.DynamicFormBuilderSystem.mapper;

import com.project.DynamicFormBuilderSystem.entity.Field;
import com.project.DynamicFormBuilderSystem.entity.FieldOption;
import com.project.DynamicFormBuilderSystem.response.FieldResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FieldMapper {
    public FieldResponse toResponse(Field field, List<String> options) {

        FieldResponse res = new FieldResponse();
        res.setId(field.getId());
        res.setLabel(field.getLabel());
        res.setType(field.getType());
        res.setDisplayOrder(field.getDisplayOrder());
        res.setRequired(field.getRequired());

        res.setOptions(options != null ? options : new ArrayList<>());

        return res;
    }
}
