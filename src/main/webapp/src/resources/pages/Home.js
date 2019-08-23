import React from "react";
import "../styles/Home.css";

const Home = () => {
  return (
      <div className="HomeWrapper">
        <div className="home">
          <div className="title">
            <span>
              DNS<p>ite</p>
            </span>
            <p>A web-based control panel for PowerDNS</p>
          </div>
          <div className="content">
            DNSite is a management website for PostgreSQL based database used by PowerDNS.
            Its main purpose is to allow making multiple changes to the data stored in database in a simple, fast and comfortable way.
            If you would like to learn more please read attached documentation.
          </div>
          </div>
          <div className="authors">
            <p>App created by:</p>
            <div className="authorsWrapper">
                <a href="https://github.com/Loniowsky">Jarosław Cierpich</a><p>|</p>
                <a href="https://github.com/arokasprz100">Arkadiusz Kasprzak</a><p>|</p>
                <a href="https://github.com/JakubKowalski1997">Jakub Kowalski</a><p>|</p>
                <a href="https://github.com/pierwiastekzminusjeden">Krystian Molenda</a><p>|</p>
                <a href="https://github.com/Pasik97">Konrad Pasik</a>
            </div>
      </div>
    </div>
  );
};

export default Home;
