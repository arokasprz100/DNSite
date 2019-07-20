import React from "react";
import "../styles/MainPage.css";
import { Route, Switch } from "react-router-dom";
import Home from "../pages/Home";
import Supermasters from "../pages/Supermasters";
import Zones from "../pages/Zones";
import Administration from "../pages/Administration";
import ChangePassword from "../pages/ChangePassword";

const MainPage = () => {
  return (
    <>
      <Switch>
        <Route path="/dnsite" exact component={Home} />
        <Route path="/supermasters" component={Supermasters} />
        <Route path="/zones" component={Zones} />
        <Route path="/administration" component={Administration} />
        <Route path="/changePassword" component={ChangePassword} />
      </Switch>
    </>
  );
};

export default MainPage;
