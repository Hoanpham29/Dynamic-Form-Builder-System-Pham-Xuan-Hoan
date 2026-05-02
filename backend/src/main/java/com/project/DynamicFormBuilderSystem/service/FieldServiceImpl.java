package com.project.DynamicFormBuilderSystem.service;

import com.project.DynamicFormBuilderSystem.entity.Field;
import com.project.DynamicFormBuilderSystem.entity.FieldOption;
import com.project.DynamicFormBuilderSystem.entity.Form;
import com.project.DynamicFormBuilderSystem.mapper.FieldMapper;
import com.project.DynamicFormBuilderSystem.mapper.FormMapper;
import com.project.DynamicFormBuilderSystem.repository.FieldOptionRepository;
import com.project.DynamicFormBuilderSystem.repository.FieldRepository;
import com.project.DynamicFormBuilderSystem.repository.FormRepository;
import com.project.DynamicFormBuilderSystem.request.CreateFieldRequest;
import com.project.DynamicFormBuilderSystem.request.UpdateFieldRequest;
import com.project.DynamicFormBuilderSystem.response.FieldResponse;
import com.project.DynamicFormBuilderSystem.response.FormResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FormRepository formRepository;
    private final FieldOptionRepository fieldOptionRepository;
    private final FieldMapper fieldMapper;
    private final FormMapper formMapper;

    public FieldServiceImpl(FieldRepository fieldRepository,
                            FormRepository formRepository,
                            FieldOptionRepository fieldOptionRepository,
                            FieldMapper fieldMapper,
                            FormMapper formMapper) {
        this.fieldRepository = fieldRepository;
        this.formRepository = formRepository;
        this.fieldOptionRepository = fieldOptionRepository;
        this.fieldMapper = fieldMapper;
        this.formMapper = formMapper;
    }

    @Transactional
    @Override
    public FieldResponse createNewField(Long formId, CreateFieldRequest createFieldRequest) {

        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Field field = new Field();
        field.setLabel(createFieldRequest.getLabel());
        field.setType(createFieldRequest.getType());
        field.setDisplayOrder(createFieldRequest.getDisplayOrder());
        field.setRequired(createFieldRequest.getRequired());
        field.setForm(form);

        if ("select".equalsIgnoreCase(createFieldRequest.getType())) {

            List<FieldOption> options = createFieldRequest.getOptions().stream()
                    .map(opt -> {
                        FieldOption fo = new FieldOption();
                        fo.setValue(opt.getValue());
                        fo.setField(field);
                        return fo;
                    })
                    .toList();

            field.setOptions(options);
        }

        Field saved = fieldRepository.save(field);

        return fieldMapper.toResponse(saved,
                "select".equalsIgnoreCase(saved.getType())
                        ? saved.getOptions().stream().map(FieldOption::getValue).toList()
                        : null
        );
    }

    @Transactional
    @Override
    public FieldResponse updateField(Long formId, Long id, UpdateFieldRequest updateFieldRequest) {

        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (field.getForm().getId() != (formId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (updateFieldRequest.getLabel() != null) field.setLabel(updateFieldRequest.getLabel());
        if (updateFieldRequest.getDisplayOrder() != null) field.setDisplayOrder(updateFieldRequest.getDisplayOrder());
        if (updateFieldRequest.getRequired() != null) field.setRequired(updateFieldRequest.getRequired());

        String newType = updateFieldRequest.getType() != null ? updateFieldRequest.getType() : field.getType();
        field.setType(newType);

        if ("select".equalsIgnoreCase(newType)) {

            field.getOptions().clear();

            List<FieldOption> newOptions = updateFieldRequest.getOptions().stream()
                    .map(opt -> {
                        FieldOption fo = new FieldOption();
                        fo.setValue(opt);
                        fo.setField(field);
                        return fo;
                    })
                    .toList();

            field.getOptions().addAll(newOptions);

        } else {
            field.getOptions().clear();
        }

        Field saved = fieldRepository.save(field);

        return fieldMapper.toResponse(saved,
                "select".equalsIgnoreCase(saved.getType())
                        ? fieldOptionRepository.findByFieldId(saved.getId())
                          .stream()
                          .map(FieldOption::getValue)
                          .toList()
                        : null
        );
    }

    @Transactional
    @Override
    public void deleteField(Long formId, Long id) {

        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (field.getForm().getId() != (formId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        fieldRepository.delete(field);
    }
}
