package com.project.DynamicFormBuilderSystem.service;

import com.project.DynamicFormBuilderSystem.entity.*;
import com.project.DynamicFormBuilderSystem.mapper.FieldMapper;
import com.project.DynamicFormBuilderSystem.mapper.FormMapper;
import com.project.DynamicFormBuilderSystem.repository.FieldOptionRepository;
import com.project.DynamicFormBuilderSystem.repository.FieldRepository;
import com.project.DynamicFormBuilderSystem.repository.FormRepository;
import com.project.DynamicFormBuilderSystem.repository.SubmissionRepository;
import com.project.DynamicFormBuilderSystem.request.AnswerRequest;
import com.project.DynamicFormBuilderSystem.request.SubmissionRequest;
import com.project.DynamicFormBuilderSystem.response.AnswerResponse;
import com.project.DynamicFormBuilderSystem.response.FieldResponse;
import com.project.DynamicFormBuilderSystem.response.FormResponse;
import com.project.DynamicFormBuilderSystem.response.SubmissionResponse;
import com.project.DynamicFormBuilderSystem.util.FindAuthenticatedUser;
import org.hibernate.annotations.processing.Find;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final FormRepository formRepository;
    private final FieldRepository fieldRepository;
    private final SubmissionRepository submissionRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;
    private final FormMapper formMapper;
    private final FieldOptionRepository fieldOptionRepository;
    private final FieldMapper fieldMapper;

    public SubmissionServiceImpl(FormRepository formRepository, FieldRepository fieldRepository, SubmissionRepository submissionRepository, FindAuthenticatedUser findAuthenticatedUser, FormMapper formMapper, FieldOptionRepository fieldOptionRepository, FieldMapper fieldMapper) {
        this.formRepository = formRepository;
        this.fieldRepository = fieldRepository;
        this.submissionRepository = submissionRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
        this.formMapper = formMapper;
        this.fieldOptionRepository = fieldOptionRepository;
        this.fieldMapper = fieldMapper;
    }

    @Override
    public List<SubmissionResponse> getSubmissions() {

        User currentUser = findAuthenticatedUser.getAuthenticatedUser();

        List<Submission> submissions =
                submissionRepository.findByUserWithDetails(currentUser);

        return submissions.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<FormResponse> getActiveForms() {

        List<Form> forms = formRepository.findAllActiveForms();

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
    public void submitForm(Long id, SubmissionRequest submissionRequest) {

        Form form = formRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Form not found"));

        User currentUser = findAuthenticatedUser.getAuthenticatedUser();

        Submission submission = new Submission();
        submission.setForm(form);
        submission.setUser(currentUser);

        List<SubmissionAnswer> answers = new ArrayList<>();

        for (AnswerRequest answerRequest : submissionRequest.getAnswers()) {

            Field field = fieldRepository.findById(answerRequest.getFieldId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Field not found"));

            if (field.getForm().getId() != (id)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Field does not belong to this form");
            }

            validateValue(field, answerRequest.getValue());

            SubmissionAnswer answer = new SubmissionAnswer();
            answer.setField(field);
            answer.setValue(answerRequest.getValue());
            answer.setSubmission(submission);

            answers.add(answer);
        }

        submission.setAnswers(answers);

        submissionRepository.save(submission);
    }

    private void validateValue(Field field, String value) {

        if (value == null || value.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    field.getLabel() + " is required");
        }

        switch (field.getType().toLowerCase()) {

            case "text":
                if (value.length() > 200) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Text max length is 200");
                }
                break;

            case "number":
                try {
                    Double.parseDouble(value);
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            field.getLabel() + " must be a number");
                }
                break;

            case "date":
                try {
                    java.time.LocalDate.parse(value);
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Invalid date format (yyyy-MM-dd)");
                }
                break;

            case "color":
                if (!value.matches("^#([A-Fa-f0-9]{6})$")) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Invalid HEX color");
                }
                break;

            case "select":
                boolean exists = field.getOptions().stream()
                        .anyMatch(opt -> opt.getValue().equals(value));

                if (!exists) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Invalid option for " + field.getLabel());
                }
                break;

            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Unsupported field type");
        }
    }

    private SubmissionResponse mapToResponse(Submission submission) {

        SubmissionResponse res = new SubmissionResponse();

        res.setId(submission.getId());
        res.setFormId(submission.getForm().getId());
        res.setCreatedAt(submission.getCreatedAt());

        if (submission.getAnswers() != null) {

            res.setAnswers(
                    submission.getAnswers().stream()
                            .map(answer -> {

                                AnswerResponse a = new AnswerResponse();

                                a.setFieldId(answer.getField().getId());
                                a.setLabel(answer.getField().getLabel());
                                a.setType(answer.getField().getType());
                                a.setValue(answer.getValue());

                                return a;
                            })
                            .toList()
            );
        }

        return res;
    }
}
