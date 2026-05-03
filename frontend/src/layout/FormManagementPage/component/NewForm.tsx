import { useState } from "react";

export const NewForm = ({ addForm, onSuccess }: any) => {

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [displayOrder, setDisplayOrder] = useState(1);

    const submit = async () => {

        const token = localStorage.getItem("token");

        const res = await fetch("http://localhost:8080/api/admin/forms", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({
                title,
                description,
                status: "draft",
                displayOrder
            })
        });

        const data = await res.json();

        addForm(data);
        onSuccess();

        setTitle("");
        setDescription("");
    };

    return (
        <div>
            <div className="mb-2">
            <label className="form-label">Title</label>
            <input
                className="form-control mb-2"
                placeholder="Title"
                value={title}
                onChange={e => setTitle(e.target.value)}
            />
            </div>
            <div className="mb-2">
            <label className="form-label">Description</label>
            <textarea
                className="form-control mb-2"
                placeholder="Description"
                value={description}
                onChange={e => setDescription(e.target.value)}
            />
            </div>

            <div className="mb-2">
                <label className="form-label">Display Order</label>

                <input
    type="number"
    className="form-control"
    value={displayOrder}
    onChange={(e) => setDisplayOrder(Number(e.target.value))}
/>
            </div>
            <button className="btn btn-primary w-100" onClick={submit}>
                Create Draft
            </button>

        </div>
    );
};