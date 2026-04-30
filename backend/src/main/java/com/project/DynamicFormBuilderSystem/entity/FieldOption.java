package com.project.DynamicFormBuilderSystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "field_options")
public class FieldOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    public FieldOption() {
    }

    public FieldOption(String value, Field field) {
        this.value = value;
        this.field = field;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
