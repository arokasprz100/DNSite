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
            Lorem, ipsum dolor sit amet consectetur adipisicing elit. Nesciunt
            voluptatibus earum amet impedit obcaecati ad vel saepe ullam mollitia
            possimus aperiam aliquam deleniti, totam odit est dicta cum quisquam
            sapiente.
          </div>
          </div>
          <div className="authors">
            <p>App created by:</p>
            <div className="authorsWrapper">
                <p className="Author">Jarosław Cierpich</p>
                <p className="Author">Arkadiusz Kasprzak</p>
                <p className="Author">Jakub Kowalski</p>
                <p className="Author">Krystian Molenda</p>
                <p className="Author">Konrad Pasik</p>
            </div>
      </div>
    </div>
  );
};

export default Home;
