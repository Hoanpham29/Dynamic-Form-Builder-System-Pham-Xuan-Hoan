import { Link, useHistory } from "react-router-dom";
import { useEffect, useState } from "react";
import { UserModel } from "../../models/UserModel";

export const Navbar = () => {

  const history = useHistory();
  const [users, setUsers] = useState<UserModel | null>(null);
  const [httpError, setHttpError] = useState<string | null>(null);

  const token = localStorage.getItem("token");

  useEffect(() => {

    const fetchUser = async () => {

      if (!token) {
        setUsers(null);
        return;
      }

      try {
        const response = await fetch("http://localhost:8080/api/users", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
          }
        });

        if (response.status === 401) {
          localStorage.removeItem("token");
          setUsers(null);
          history.push("/login");
          return;
        }

        if (!response.ok) {
          throw new Error("Something went wrong!");
        }

        const data = await response.json();
        setUsers(data);

      } catch (error: any) {
        setHttpError(error.message);
        setUsers(null);
      }
    };

    fetchUser();

  }, [token, history]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    setUsers(null);
    history.push("/login");
  };

  if (httpError) {
    return <p className="text-danger">{httpError}</p>;
  }

  return (
    <nav className='navbar navbar-dark border-bottom'>
      <div className='container-fluid'>

        <span className='fw-semibold' style={{ fontSize: '24px' }}>
          DFBS - Phạm Xuân Hoàn
        </span>

        <div className="dropdown">

          <button
            className="btn border-0 p-0"
            type="button"
            data-bs-toggle="dropdown"
          >
            <i className="bi bi-person-circle" style={{ fontSize: 30 }}></i>
          </button>

          <ul className="dropdown-menu dropdown-menu-end" style={{ minWidth: "190px" }}>

            <li>
              <h5 className="dropdown-item-text">
                Hi, {users?.fullName || "Guest"}
              </h5>
            </li>

            <li>
              <Link to="/profile" className="dropdown-item">
                Profile
              </Link>
            </li>

            <li><hr className="dropdown-divider" /></li>

            <li>
              <button
                className="dropdown-item text-danger"
                onClick={handleLogout}
              >
                Logout
              </button>
            </li>

          </ul>
        </div>

      </div>
    </nav>
  );
};