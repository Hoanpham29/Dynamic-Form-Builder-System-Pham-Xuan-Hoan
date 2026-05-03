import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import { SpinnerLoading } from "../Utils/SpinnerLoading";

export const SubmittedFormsPage = () => {
  const [submissions, setSubmissions] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const history = useHistory();

  useEffect(() => {
    const fetchData = async () => {
      const token = localStorage.getItem("token");

      if (!token) {
        history.push("/login");
        return;
      }

      try {
        const response = await fetch(
          "http://localhost:8080/api/submissions",
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (response.status === 401) {
          localStorage.removeItem("token");
          history.push("/login");
          return;
        }

        if (!response.ok) {
          throw new Error("Failed to load submissions");
        }

        const data = await response.json();
        setSubmissions(data);
      } catch (err: any) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [history]);

  if (loading) return <SpinnerLoading />;
  if (error) return <p className="text-danger">{error}</p>;

  const formatValue = (ans: any) => {
    if (ans.type === "date") {
      return new Date(ans.value).toLocaleDateString();
    }
    return ans.value;
  };

  return (
    <div className="container mt-4 w-75">
      <h3 className="mb-4">My Submitted Forms</h3>

      {submissions.length === 0 && (
        <p className="text-muted">No submissions yet.</p>
      )}

      {submissions.map((sub) => (
        <div key={sub.id} className="card mb-3 shadow-sm">

          <div className="card-header d-flex justify-content-between">
            <strong>Form ID: {sub.formId}</strong>
            <small>
              {new Date(sub.createdAt).toLocaleString()}
            </small>
          </div>

          <div className="card-body">

            {sub.answers.map((ans: any) => (
              <div key={ans.fieldId} className="mb-3 border-bottom pb-2">

                <div className="fw-semibold">
                  {ans.label}
                </div>
                <div className="d-flex align-items-center gap-2">

                  <strong>Answer:</strong>

                  {ans.type === "color" ? (
                    <div className="d-flex align-items-center gap-2">
                      <span>{ans.value}</span>
                      <div
                        style={{
                          width: "40px",
                          height: "20px",
                          backgroundColor: ans.value,
                          border: "2px solid #ccc"
                        }}
                        title={ans.value}
                      />
                    </div>
                  ) : (
                    <span>{formatValue(ans)}</span>
                  )}

                </div>

              </div>
            ))}

          </div>

        </div>
      ))}
    </div>
  );
};