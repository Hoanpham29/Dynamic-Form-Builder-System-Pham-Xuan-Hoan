import { useState } from "react";
import { FieldModel } from "../../../models/FieldModel";

export const FieldItem: React.FC<{
    field: FieldModel;
    formId: number;
    isDraft: boolean;
    onUpdate: (field: FieldModel) => void;
    onDelete: (id: number) => void;
}> = ({ field, formId, isDraft, onUpdate, onDelete }) => {

    const [editField, setEditField] = useState<FieldModel>(field);

    const handleChange = (key: keyof FieldModel, value: any) => {
        setEditField(prev => ({
            ...prev,
            [key]: value
        }));
    };

    const saveUpdate = async () => {
        const token = localStorage.getItem("token");

        const res = await fetch(
            `http://localhost:8080/api/forms/${formId}/fields/${field.id}`,
            {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(editField)
            }
        );

        const data = await res.json();
        onUpdate(data);
    };

    const handleDelete = async () => {
        const token = localStorage.getItem("token");

        await fetch(
            `http://localhost:8080/api/forms/${formId}/fields/${field.id}`,
            {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
        );

        onDelete(field.id);
    };

    return (
        <div className="border rounded p-2 mb-2">

            <input
                className="form-control mb-2"
                value={editField.label}
                disabled={!isDraft}
                onChange={(e) => handleChange("label", e.target.value)}
            />

            <select
                className="form-select mb-2"
                value={editField.type}
                disabled={!isDraft}
                onChange={(e) => handleChange("type", e.target.value)}
            >
                <option value="text">Text</option>
                <option value="number">Number</option>
                <option value="date">Date</option>
                <option value="color">Color</option>
                <option value="select">Select</option>
            </select>

            <div className="form-check mb-2">
                <input
                    type="checkbox"
                    className="form-check-input"
                    checked={editField.required}
                    disabled={!isDraft}
                    onChange={(e) =>
                        handleChange("required", e.target.checked)
                    }
                />
                <label className="form-check-label">
                    Required
                </label>
            </div>

            <input
                type="number"
                className="form-control mb-2"
                value={editField.displayOrder}
                disabled={!isDraft}
                onChange={(e) =>
                    handleChange("displayOrder", Number(e.target.value))
                }
            />

            {editField.type === "select" && (
                <input
                    className="form-control mb-2"
                    placeholder="Options comma separated"
                    value={editField.options?.join(",") || ""}
                    disabled={!isDraft}
                    onChange={(e) =>
                        handleChange("options", e.target.value.split(","))
                    }
                />
            )}

            {isDraft && (
                <div className="d-flex gap-2">
                    <button className="btn btn-primary btn-sm" onClick={saveUpdate}>
                        Save
                    </button>

                    <button className="btn btn-danger btn-sm" onClick={handleDelete}>
                        Delete
                    </button>
                </div>
            )}

        </div>
    );
};