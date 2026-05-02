import { Link } from "react-router-dom";

export const Footer = () => {
    return (
        <div className="main-color bg-secondary">
            <footer className=" container-fluid d-flex flex-wrap justify-content-between
            align-items-center py-3 main-color">
                <p className="col-md-4 mb-0 text-white">© Dynamic Form Builder System</p>
                <ul className="nav navbar-dark col-md-4 justify-content-end">
                    <li className="nav-item">
                    <a
                        className="nav-link px-2 text-white"
                        href="https://github.com/Hoanpham29/Dynamic-Form-Builder-System-Pham-Xuan-Hoan"
                        target="_blank"
                        rel="noreferrer"
                    >
                        <i className="bi bi-github"></i><span className="ms-1">Here's my repository </span>
                    </a>
                    </li>
                </ul>
            </footer>
        </div>
    );
}