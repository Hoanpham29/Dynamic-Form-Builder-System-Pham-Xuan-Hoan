package com.project.DynamicFormBuilderSystem.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "form_id", nullable = false)
    private Form form;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubmissionAnswer> answers;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Submission() {
    }

    public Submission(LocalDateTime createdAt, Form form, List<SubmissionAnswer> answers, User user) {
        this.createdAt = createdAt;
        this.form = form;
        this.answers = answers;
        this.user = user;
    }
}
