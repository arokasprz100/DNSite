import React, { Component } from "react";
import { BrowserRouter as Router } from "react-router-dom";
import "../styles/App.css";
import Header from "./Header";
import Navigation from "./Navigation";
import UserBlock from "./UserBlock";
import MainPage from "./MainPage";
import Footer from "./Footer";

class App extends Component {
  render() {
    return (
      <Router>
        <div className="app">
          <header>
            {<Header />}
            {<Navigation />}
          </header>
          <main>
            <aside>{<UserBlock />}</aside>
            <article>{<MainPage />}</article>
          </main>
          <footer>{<Footer />}</footer>
        </div>
      </Router>
    );
  }
}

export default App;
