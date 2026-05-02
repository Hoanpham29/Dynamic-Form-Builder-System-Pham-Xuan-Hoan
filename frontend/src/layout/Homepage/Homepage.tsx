import { useState, useEffect } from "react";
import { FormModel } from "../../models/FormModel";
import { SpinnerLoading } from "../Utils/SpinnerLoading";
import { useHistory } from "react-router-dom";
import { NewTodoForm } from "./components/NewTodoForm";
import { FormItem } from "./components/FormItem";

export const HomePage = () => {

    const [forms, setForms] = useState<FormModel[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [httpError, setHttpError] = useState<string | null>(null);

    const history = useHistory();

    useEffect(() => {
        const fetchForms = async () => {
            const token = localStorage.getItem("token");

            if (!token) {
                history.push("/login");
                return;
            }

            try {
                const response = await fetch("http://localhost:8080/api/forms/active", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${token}`
                    }
                });

                if (response.status === 401) {
                    localStorage.removeItem("token");
                    history.push("/login");
                    return;
                }

                if (!response.ok) {
                    history.push("/login");
                    throw new Error("Something went wrong!");
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


    if (isLoading) {
        return <SpinnerLoading />;
    }

    if (httpError) {
        return <p className="text-danger">{httpError}</p>;
    }

    return (
        <div className='mt-5 container w-75'>
            {forms.map(form => (
                <FormItem
                    key={form.id}
                    form={form}
                />
            ))}
        </div>
    );
};