import React from "react";
import "../styles/Supermasters.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import ReusableTable from "../components/ReusableTable.js"

class Records extends React.Component {

    // make sure those are NOT arrow functions due to 'this' binding
    fetchValueConstraints()
    {
        Promise.all([
            fetch('http://localhost:8001/records/types'),
            fetch('http://localhost:8001/domains/domainIds')
        ])
        .then(([res1, res2]) => Promise.all([res1.json(), res2.json()]))
        .then(([types, domainIds]) => {
            let valueConstraints = JSON.parse(JSON.stringify(this.state.valueConstraints));
            valueConstraints['types'] = types;
            valueConstraints['domainIds'] = domainIds;
            this.setState ({
                valueConstraints : valueConstraints
            });
        })
    }


    render()
    {
        // TODO: change "" to nulls
        let emptyDataExample = { id : '', domainId : '', name : '', type : '', content : '', ttl : '', disabled : '' };
        const columns = [
            { Header : "ID", accessor : "id", type: "none" },
            { Header : "DomainID", accessor : "domainId", type: "select" },
            { Header : "Name", accessor : "name", type: "text" },
            { Header : "Type", accessor : "type", type: "select" },
            { Header : "Content", accessor : "content", type: "text" },
            { Header : "TTL", accessor : "ttl", type: "number" },
            { Header : "Disabled", accessor : "disabled", type: "bool" },
        ];
        return (
            <div>
                <ReusableTable ref = "recordsTable"
                fetchValueConstraints = {this.fetchValueConstraints}
                resourcesURLBase = "http://localhost:8001/records/"
                resourcesSelectURL = "all"
                emptyDataExample = {emptyDataExample}
                columns = {columns}
                resourceName = "record" />
            </div>
        );
    }
}


export default Records;