package com.project.DynamicFormBuilderSystem.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "fields")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private String type;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(nullable = false)
    private boolean required;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "form_id", nullable = false)
    private Form form;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FieldOption> options;

    public Field() {
    }

    public Field(String label, String type, Integer displayOrder, boolean required, Date createdAt, Date updatedAt, Form form, List<FieldOption> options) {
        this.label = label;
        this.type = type;
        this.displayOrder = displayOrder;
        this.required = required;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.form = form;
        this.options = options;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public List<FieldOption> getOptions() {
        return options;
    }

    public void setOptions(List<FieldOption> options) {
        this.options = options;
    }

}
