import { useState, useEffect } from "react";
import { SpinnerLoading } from "../Utils/SpinnerLoading";
import { useHistory } from "react-router-dom";
import { FormModel } from "../../models/FormModel";
import { FormItem } from "./component/FormItem";
import { NewForm } from "./component/NewForm";


export const FormManagementPage = () => {

    const [forms, setForms] = useState<FormModel[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [httpError, setHttpError] = useState<string | null>(null);
    const draftForms = forms.filter(f => f.status === "draft");
    const activeForms = forms.filter(f => f.status === "active");
    const history = useHistory();

    useEffect(() => {
        const fetchForms = async () => {

            const token = localStorage.getItem("token");
            if (!token) {
                history.push("/login");
                setIsLoading(false);
                return;
            }

            try {
                const response = await fetch("http://localhost:8080/api/admin/forms", {
                    headers: {
                        "Authorization": `Bearer ${token}`
                    }
                });

                if (response.status === 401) {
                    localStorage.removeItem("token");
                    history.push("/login");
                    return;
                }

                if (!response.ok) {
                    throw new Error("Failed to fetch forms");
                }

                const data = await response.json();
                setForms(data);

            } catch (error: any) {
                setHttpError(error.message);
            } finally {
                setIsLoading(false);
            }
        };

        fetchForms();
    }, [history]);

    const addForm = (newForm: FormModel) => {
        setForms(prev => [newForm, ...prev]);
    };

    const removeForm = (id: number) => {
        setForms(prev => prev.filter(f => f.id !== id));
    };

    const updateForm = (updated: FormModel) => {
        setForms(prev =>
            prev.map(f => f.id === updated.id ? updated : f)
        );
    };

    if (isLoading) return <SpinnerLoading />;
    if (httpError) return <p className="text-danger">{httpError}</p>;

    return (
        <div className='mt-5 container w-75'>

            <div className='card bg-secondary bg-opacity-10'>
                <div
                    className='card-header text-center'
                    data-bs-toggle='collapse'
                    data-bs-target="#collapsedAdd"
                    style={{ cursor: 'pointer' }}
                >
                    <span className="fw-medium" style={{ fontSize: '23px' }}>
                        New Form
                    </span>
                </div>

                <div id="collapsedAdd" className='collapse'>
                    <div className='card-body'>
                        <NewForm
                            addForm={addForm}
                            onSuccess={() => window.location.reload()}
                        />
                    </div>
                </div>
            </div>

            {/* DRAFT */}
<h5 className="mt-4 text-warning">Draft Forms</h5>
{draftForms.map(form => (
    <FormItem
        key={form.id}
        form={form}
        onDelete={removeForm}
        onActivate={updateForm}
    />
))}

{/* ACTIVE */}
<h5 className="mt-4 text-success">Active Forms</h5>
{activeForms.map(form => (
    <FormItem
        key={form.id}
        form={form}
        onDelete={removeForm}
        onActivate={updateForm}
    />
))}

        </div>
    );
};