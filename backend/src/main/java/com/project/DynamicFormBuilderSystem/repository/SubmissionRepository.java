package com.project.DynamicFormBuilderSystem.repository;

import com.project.DynamicFormBuilderSystem.entity.Form;
import com.project.DynamicFormBuilderSystem.entity.Submission;
import com.project.DynamicFormBuilderSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission,Long> {
    @Query("""
    SELECT DISTINCT s FROM Submission s
    LEFT JOIN FETCH s.answers a
    LEFT JOIN FETCH a.field
    WHERE s.user = :user
    """)
    List<Submission> findByUserWithDetails(@Param("user") User user);

    @Query("""
    SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
    FROM Submission s
    WHERE s.form.id = :formId
""")
    boolean existsByFormId(@Param("formId") Long formId);
}
