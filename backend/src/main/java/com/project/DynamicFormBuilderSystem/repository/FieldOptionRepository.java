package com.project.DynamicFormBuilderSystem.repository;

import com.project.DynamicFormBuilderSystem.entity.FieldOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldOptionRepository extends JpaRepository<FieldOption, Long> {

    List<FieldOption> findByFieldId(Long fieldId);

}
