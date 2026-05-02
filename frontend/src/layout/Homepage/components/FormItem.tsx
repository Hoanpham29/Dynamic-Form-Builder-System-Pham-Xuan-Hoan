import { useState } from "react";
import { useHistory } from "react-router-dom";
import { FormModel } from "../../../models/FormModel";

export const FormItem: React.FC<{ form: FormModel; onSuccess?: Function }> = (props) => {

    const collapseId = `form-${props.form.id}`;
    const history = useHistory();

    const [answers, setAnswers] = useState<Record<number, string>>({});
    const [loading, setLoading] = useState(false);

    const handleChange = (fieldId: number, value: string) => {
        setAnswers(prev => ({
            ...prev,
            [fieldId]: value
        }));
    };

    const handleSubmit = async () => {
        setLoading(true);

        const token = localStorage.getItem("token");

        try {
            await fetch(`http://localhost:8080/api/forms/${props.form.id}/submit`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify({
                    answers: Object.entries(answers).map(([fieldId, value]) => ({
                        fieldId: Number(fieldId),
                        value
                    }))
                })
            });

            alert("Submit success!");
            setAnswers({});
            props.onSuccess?.();

        } catch (err) {
            console.log(err);
        } finally {
            setLoading(false);
        }
    };

    const renderField = (field: any) => {
        switch (field.type) {

            case "text":
                return (
                    <input
                        type="text"
                        className="form-control"
                        value={answers[field.id] || ""}
                        onChange={(e) => handleChange(field.id, e.target.value)}
                    />
                );

            case "number":
                return (
                    <input
                        type="number"
                        className="form-control"
                        value={answers[field.id] || ""}
                        onChange={(e) => handleChange(field.id, e.target.value)}
                    />
                );

            case "date":
                return (
                    <input
                        type="date"
                        className="form-control"
                        value={answers[field.id] || ""}
                        onChange={(e) => handleChange(field.id, e.target.value)}
                    />
                );

            case "select":
                return (
                    <select
                        className="form-select"
                        value={answers[field.id] || ""}
                        onChange={(e) => handleChange(field.id, e.target.value)}
                    >
                        <option value="">Select...</option>

                        {field.options?.map((opt: string, idx: number) => (
                            <option key={idx} value={opt}>
                                {opt}
                            </option>
                        ))}
                    </select>
                );

            case "color":
                return (
                    <input
                        type="color"
                        className="form-control form-control-color"
                        value={answers[field.id] || "#000000"}
                        onChange={(e) => handleChange(field.id, e.target.value)}
                        title="Choose color"
                    />
                );

            default:
                return (
                    <input
                        type="text"
                        className="form-control"
                        value={answers[field.id] || ""}
                        onChange={(e) => handleChange(field.id, e.target.value)}
                    />
                );
        }
    };

    return (
        <div className="card mt-3">

            <div
                className="card-header d-flex justify-content-between align-items-center"
                data-bs-toggle="collapse"
                data-bs-target={`#${collapseId}`}
                style={{ cursor: "pointer", height: "55px" }}
            >
                <span className="fw-medium" style={{ fontSize: "24px" }}>
                    {props.form.title}
                </span>
            </div>

            <div id={collapseId} className="collapse show">
                <div className="card-body">

                    <div className="mt-2 d-flex align-items-center justify-content-center">
                        <h5 className="text-black">
                            {props.form.description}
                        </h5>
                    </div>
                    {props.form.fields?.map((field: any) => (
                        <div key={field.id} className="mb-3 mt-3">

                            <label className="form-label">
                                {field.label}
                            </label>

                            {renderField(field)}

                        </div>
                    ))}

                    <button
                        className="btn btn-success w-100"
                        onClick={handleSubmit}
                        disabled={loading}
                    >
                        {loading ? "Submitting..." : "Submit Form"}
                    </button>

                </div>
            </div>

        </div>
    );
};