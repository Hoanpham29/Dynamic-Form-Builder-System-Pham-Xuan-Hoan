package com.project.DynamicFormBuilderSystem.repository;

import com.project.DynamicFormBuilderSystem.entity.Field;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FieldRepository extends CrudRepository<Field,Long> {
    List<Field> findByFormId(Long formId);
}
