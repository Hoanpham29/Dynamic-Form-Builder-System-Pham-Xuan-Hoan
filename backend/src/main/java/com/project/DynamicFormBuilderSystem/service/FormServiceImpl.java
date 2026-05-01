package com.project.DynamicFormBuilderSystem.service;

import com.project.DynamicFormBuilderSystem.entity.Field;
import com.project.DynamicFormBuilderSystem.entity.FieldOption;
import com.project.DynamicFormBuilderSystem.entity.Form;
import com.project.DynamicFormBuilderSystem.mapper.FieldMapper;
import com.project.DynamicFormBuilderSystem.mapper.FormMapper;
import com.project.DynamicFormBuilderSystem.repository.FieldOptionRepository;
import com.project.DynamicFormBuilderSystem.repository.FieldRepository;
import com.project.DynamicFormBuilderSystem.repository.FormRepository;
import com.project.DynamicFormBuilderSystem.request.CreateFormRequest;
import com.project.DynamicFormBuilderSystem.response.FieldResponse;
import com.project.DynamicFormBuilderSystem.response.FormResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FormServiceImpl implements FormService {

    private final FormRepository formRepository;
    private final FieldRepository fieldRepository;
    private final FieldOptionRepository fieldOptionRepository;
    private final FormMapper formMapper;
    private final FieldMapper fieldMapper;

    public FormServiceImpl(FormRepository formRepository,
                           FieldRepository fieldRepository,
                           FieldOptionRepository fieldOptionRepository,
                           FormMapper formMapper,
                           FieldMapper fieldMapper) {
        this.formRepository = formRepository;
        this.fieldRepository = fieldRepository;
        this.fieldOptionRepository = fieldOptionRepository;
        this.formMapper = formMapper;
        this.fieldMapper = fieldMapper;
    }

    @Override
    public List<FormResponse> getAll() {

        List<Form> forms = formRepository.findAll();

        return forms.stream()
                .map(form -> {

                    List<Field> fields = fieldRepository.findByFormId(form.getId());

                    List<FieldResponse> fieldResponses = fields.stream()
                            .map(field -> {

                                List<String> options = null;

                                if ("select".equalsIgnoreCase(field.getType())) {
                                    options = fieldOptionRepository.findByFieldId(field.getId())
                                            .stream()
                                            .map(FieldOption::getValue)
                                            .toList();
                                }

                                return fieldMapper.toResponse(field, options);
                            })
                            .toList();

                    return formMapper.toResponse(form, fieldResponses);
                })
                .toList();
    }

    @Override
    @Transactional
    public FormResponse createNewForm(CreateFormRequest request) {

        Form form = new Form();
        form.setTitle(request.getTitle());
        form.setDescription(request.getDescription());
        form.setStatus(request.getStatus());
        form.setDisplayOrder(request.getDisplayOrder());

        Form saved = formRepository.save(form);

        return formMapper.toResponse(saved, null);
    }

    @Override
    @Transactional
    public FormResponse findFormById(long id) {

        Form form = formRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Field> fields = fieldRepository.findByFormId(id);

        List<FieldResponse> fieldResponses = fields.stream()
                .map(field -> {

                    List<String> options = null;

                    if ("select".equalsIgnoreCase(field.getType())) {
                        options = fieldOptionRepository.findByFieldId(field.getId())
                                .stream()
                                .map(FieldOption::getValue)
                                .toList();
                    }

                    return fieldMapper.toResponse(field, options);
                })
                .toList();

        return formMapper.toResponse(form, fieldResponses);
    }

    @Override
    public FormResponse updateForm(long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteForm(long id) {
        formRepository.deleteById(id);
    }
}
