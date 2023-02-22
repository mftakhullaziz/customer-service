import React from "react";
import { Switch, Route} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.css";
import "@fortawesome/fontawesome-free/js/all.js";
import "./App.css";

import Create from "./components/Create";
import Update from "./components/Update";
import DataCustomerList from "./components/DataCustomerList";
import Detail from "./components/Detail";

function App() {
  return (
    <div>
      <nav className="navbar" style={{
        display: "flex",
        justifyContent: "center",
        backgroundColor: "#FFFFFF"}}>
        <ul className="navbar-list" style={{
          display: "flex",
          justifyContent: "center",
          listStyleType: "none",
          padding: 0,
          margin: 0
        }}>
          <div className="navbar-item" style={{
            margin: "0 10px"
          }}>
            <li style={{
              fontFamily: "Roboto",
              fontSize: "2.5em",
              padding: "20px",
              fontWeight: "bolder",
              color: "#000000"
            }}>Customer Application</li>
          </div>
        </ul>
    </nav>

      <div className="container">
        <Switch>
          <Route exact path={["/", "/homepage"]} component={DataCustomerList} />
          <Route exact path="/add" component={Create} />
          <Route path="/updates/:id" component={Update} />
          <Route path="/details/:id" component={Detail} />
        </Switch>
      </div>
    </div>
  );
}

export default App;
