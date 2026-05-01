package com.project.DynamicFormBuilderSystem.repository;

import com.project.DynamicFormBuilderSystem.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FormRepository extends JpaRepository<Form,Long> {
    @Query("SELECT f FROM Form f LEFT JOIN FETCH f.fields WHERE f.id = :id")
    Optional<Form> findByIdWithFields(@Param("id") Long id);
}
