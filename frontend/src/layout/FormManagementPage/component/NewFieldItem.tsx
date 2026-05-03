import { useState } from "react";

export const NewField = ({ onAdd }: any) => {

    const [label, setLabel] = useState("");
    const [type, setType] = useState("text");

    const handleAdd = () => {
        if (!label) return;

        onAdd({
            label,
            type,
            required: false,
            displayOrder: 1
        });

        setLabel("");
    };

    return (
        <div className="card p-2 mb-2">

            <input
                className="form-control mb-2"
                placeholder="Field label"
                value={label}
                onChange={e => setLabel(e.target.value)}
            />

            <select
                className="form-select mb-2"
                value={type}
                onChange={e => setType(e.target.value)}
            >
                <option value="text">Text</option>
                <option value="number">Number</option>
                <option value="date">Date</option>
            </select>

            <button className="btn btn-success" onClick={handleAdd}>
                Add Field
            </button>

        </div>
    );
};