import { useState } from "react";
import { FormModel } from "../../../models/FormModel";
import { FieldItem } from "./FieldItem";

export const FormItem: React.FC<{
    form: FormModel;
    onDelete?: (id: number) => void;
    onActivate?: (form: FormModel) => void;
}> = ({ form, onDelete, onActivate }) => {

    const [fields, setFields] = useState(form.fields || []);
    const [displayOrder, setDisplayOrder] = useState(form.displayOrder);

    const collapseId = `form-${form.id}`;
    const isDraft = form.status?.toLowerCase() === "draft";

    const token = localStorage.getItem("token");

    // ================= DELETE FORM =================
    const handleDelete = async () => {
        if (!token) return;

        await fetch(`http://localhost:8080/api/forms/${form.id}`, {
            method: "DELETE",
            headers: {
                "Authorization": `Bearer ${token}`
            },
        });

        onDelete?.(form.id);
    };

    const handleActivate = async () => {
        if (!token) return;

        const res = await fetch(
            `http://localhost:8080/api/forms/${form.id}`,
            {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify({
                    ...form,
                    status: "active"
                })
            }
        );

        const data = await res.json();
        onActivate?.(data);
    };

    const handleAddField = async () => {
        if (!isDraft || !token) return;

        const newField = {
            label: "New Field",
            type: "text",
            required: false,
            displayOrder: fields.length + 1,
            options: []
        };

        const res = await fetch(
            `http://localhost:8080/api/forms/${form.id}/fields`,
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(newField)
            }
        );

        const data = await res.json();

        setFields(prev =>
            [...prev, data].sort((a, b) => a.displayOrder - b.displayOrder)
        );
    };

    const handleUpdateField = async (field: any) => {
        if (!token) return;

        const res = await fetch(
            `http://localhost:8080/api/forms/${form.id}/fields/${field.id}`,
            {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(field)
            }
        );

        const data = await res.json();

        setFields(prev =>
            prev
                .map(f => f.id === data.id ? data : f)
                .sort((a, b) => a.displayOrder - b.displayOrder)
        );
    };

    const handleDeleteField = async (id: number) => {
        if (!token) return;

        await fetch(
            `http://localhost:8080/api/forms/${form.id}/fields/${id}`,
            {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
        );

        setFields(prev =>
            prev
                .filter(f => f.id !== id)
                .sort((a, b) => a.displayOrder - b.displayOrder)
        );
    };

    const handleUpdateDisplayOrder = async () => {
        const token = localStorage.getItem("token");
        if (!token) return;

        try {
            const res = await fetch(
                `http://localhost:8080/api/forms/${form.id}`,
                {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${token}`
                    },
                    body: JSON.stringify({
                        ...form,
                        displayOrder: displayOrder
                    })
                }
            );

            const data = await res.json();

            onActivate?.(data);
        } catch (err) {
            console.log(err);
        }
    };

    const sortedFields = [...fields].sort(
        (a, b) => a.displayOrder - b.displayOrder
    );

    return (
        <div className="card mt-3">

            <div className="card-header d-flex align-items-center">

                <div
                    className="w-100"
                    data-bs-toggle="collapse"
                    data-bs-target={`#${collapseId}`}
                    style={{ cursor: "pointer" }}
                >
                    <span className="fw-medium" style={{ fontSize: "20px" }}>
                        {form.title}
                    </span>
                </div>

                <div className="btn-group">
                    <input
                        type="number"
                        className="form-control border-black"
                        style={{
                            width: "60px",
                            borderTopLeftRadius: "7px",
                            borderBottomLeftRadius: "7px",
                            borderTopRightRadius: "0px",
                            borderBottomRightRadius: "0px"
                        }}
                        value={displayOrder}
                        onChange={(e) => setDisplayOrder(Number(e.target.value))}
                        onBlur={handleUpdateDisplayOrder}
                    />


                    {isDraft && (
                        <button
                            className="btn btn-outline-success"
                            onClick={handleActivate}
                        >
                            Activate
                        </button>
                    )}

                    <button
                        className="btn btn-outline-danger"
                        onClick={handleDelete}
                        disabled={form.status === "active"}
                    >
                        <i className="bi bi-trash"></i>
                    </button>
                </div>
            </div>

            <div id={collapseId} className="collapse show">
                <div className="card-body">

                    <p>{form.description}</p>

                    {isDraft && (
                        <button
                            className="btn btn-sm btn-primary mb-3"
                            onClick={handleAddField}
                        >
                            + Add Field
                        </button>
                    )}

                    {sortedFields.map(field => (
                        <FieldItem
                            key={field.id}
                            field={field}
                            formId={form.id}
                            isDraft={isDraft}
                            onUpdate={handleUpdateField}
                            onDelete={handleDeleteField}
                        />
                    ))}

                </div>
            </div>
        </div>
    );
};