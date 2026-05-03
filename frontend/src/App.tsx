import { useState } from "react";
import { HomePage } from "./layout/Homepage/Homepage";
import { Navbar } from "./layout/NavbarAndFooter/Navbar";
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import { LoginPage } from "./layout/AuthPage/Loginpage";
import { RegisterPage } from "./layout/AuthPage/Registerpage";
import { Changepasswordpage } from "./layout/ChangePasswordPage/Changepasswordpage";
import { Footer } from "./layout/NavbarAndFooter/Footer";
import { SubmittedFormsPage } from "./layout/SubmittedPage/Submittedpage";
import { FormManagementPage } from "./layout/FormManagementPage/Formmanagementpage";


export const App = () => {
  const [refresh, setRefresh] = useState(false);
  const [open, setOpen] = useState(false);
  const isMobile = window.innerWidth < 768;

  return (

    <Router>
      <Switch>
        <Route path="/login">
          <LoginPage />
        </Route>

        <Route path="/register">
          <RegisterPage />
        </Route>
      </Switch>

      <div className="d-flex flex-column min-vh-100"style={{minHeight:"300px"}}>

        <Navbar />
      
        <div className="d-flex flex-grow-1 position-relative">

          <div
            className="border-end bg-white"
            style={{
              width: isMobile ? "300px" : open ? "300px" : "0px",
              overflow: "hidden",
              transition: "all 0.3s ease",
              position: isMobile ? "fixed" : "static",
              height: "100%",
              zIndex: 1050,
              transform: isMobile
                ? open
                  ? "translateX(0)"
                  : "translateX(-100%)"
                : "none"
            }}
          />

          <div className="flex-grow-1 overflow-auto p-3 bg-secondary bg-opacity-10">

            <Switch>
              <Route path="/" exact>
                <Redirect to="/home" />
              </Route>

              <Route exact path="/home">
                <HomePage />
              </Route>

              <Route exact path="/profile">
                <Changepasswordpage />
              </Route>

              <Route exact path="/submitted">
                <SubmittedFormsPage />
              </Route>

              <Route exact path="/admin">
                <FormManagementPage />
              </Route>
            </Switch>

          </div>
        </div>

        <Footer />

      </div>

    </Router>
  );
};