import React from "react";
import "../styles/Domains.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import ReusableTable from "../../ReusableTable.js"

class Domains extends React.Component {

    // make sure those are NOT arrow functions due to 'this' binding
    fetchValueConstraints()
    {
        Promise.all([
            fetch('http://localhost:8001/domains/types')
        ])
        .then(([result]) => Promise.all([result.json()]))
        .then(([types]) => {
            let valueConstraints = JSON.parse(JSON.stringify(this.state.valueConstraints));
            valueConstraints['types'] = types;
            this.setState ({
                valueConstraints : valueConstraints
            });
        })
    }


    render()
    {
        const emptyDataExample = { id : '', name : '', master : '', type : ''};
        const columns = [
            { Header : "ID", accessor : "id", type : "link" },
            { Header : "Domain Name", accessor : "name", type : "text" },
            { Header : "Domain Type", accessor : "type", type : "select" },
            { Header : "Domain Master", accessor : "master", type : "text" }
        ];

        return (
            <div>
                <ReusableTable ref="domainsTable"
                fetchValueConstraints = {this.fetchValueConstraints}
                resourcesURLBase = "http://localhost:8001/domains/"
                resourcesSelectURL = "all"
                emptyDataExample = {emptyDataExample}
                columns = {columns}
                resourceName = "domain" />
            </div>
        );
    }
}


export default Domains;
