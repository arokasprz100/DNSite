import React from "react";
import "../styles/MainPage.css";
import { Route, Switch } from "react-router-dom";
import Home from "../pages/Home";
import Supermasters from "../pages/Supermasters";
import Domains from "../pages/Domains";
import Administration from "../pages/Administration";

const MainPage = () => {
  return (
    <>
      <Switch>
        <Route path="/dnsite" exact component={Home} />
        <Route path="/supermasters" component={Supermasters} />
        <Route path="/domains" component={Domains} />
        <Route path="/administration" component={Administration} />
      </Switch>
    </>
  );
};

export default MainPage;
