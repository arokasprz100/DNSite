import React from "react"
import "../styles/Domains.css"
import 'bootstrap/dist/css/bootstrap.min.css'
import {Tab, Tabs} from 'react-bootstrap'
import ReusableTable from "../components/ReusableTable.js"
import DomainForm from "../components/DomainForm.js"

class Domain extends React.Component {

    render() {
        return (
            <div>
                <DomainForm
                    urlId = {this.props.match.params.id}
                    resourcesURLBase = "http://localhost:8001/domains/"
                />
                <Tabs defaultActiveKey="records" id="domainTabs">
                    <Tab eventKey="records" title="Records">
                        <DomainRecordsTable domainId = {this.props.match.params.id} />
                    </Tab>
                    <Tab eventKey="comments" title="Comments">
                        <DomainCommentsTable domainId = {this.props.match.params.id} />
                    </Tab>
                </Tabs>
            </div>
        );
    }
}

class DomainRecordsTable extends React.Component
{

    // make sure those are NOT arrow functions due to 'this' binding
    fetchValueConstraints()
    {
        Promise.all([
            fetch('http://localhost:8001/records/types')
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
        let emptyDataExample = { id : '', domainId : this.props.domainId, name : '', type : '', content : '', ttl : '', disabled : ''  };
        const columns = [
            { Header : "ID", accessor : "id", type: "none" },
            { Header : "Name", accessor : "name", type: "text" },
            { Header : "Type", accessor : "type", type: "select" },
            { Header : "Content", accessor : "content", type: "text" },
            { Header : "TTL", accessor : "ttl", type: "number" },
            { Header : "Disabled", accessor : "disabled", type: "bool" },
        ];
        return (
            <div>
                <ReusableTable ref = "domainRecordsTable"
                fetchValueConstraints = {this.fetchValueConstraints}
                resourcesURLBase = "http://localhost:8001/records/"
                resourcesSelectURL = {this.props.domainId}
                emptyDataExample = {emptyDataExample}
                columns = {columns}
                resourceName = "record" />
            </div>
        );
    }
}


class DomainCommentsTable extends React.Component
{
    render()
    {
        let emptyDataExample = { id : '', domainId : this.props.domainId, name : '', type : '', modifiedAt : '', comment : ''};
        const columns = [
            { Header : "ID", accessor : "id", type: "none" },
            { Header : "Name", accessor : "name", type: "text" },
            { Header : "Type", accessor : "type", type: "text" },
            { Header : "Modified at", accessor : "modifiedAt", type: "none" },
            { Header : "Comment", accessor : "comment", type: "text" },
        ];
        return (
            <div>
                <ReusableTable ref = "domainCommentsTable"
                fetchValueConstraints = { () => {} }
                resourcesURLBase = "http://localhost:8001/comments/"
                resourcesSelectURL = {this.props.domainId}
                emptyDataExample = {emptyDataExample}
                columns = {columns}
                resourceName = "comment" />
            </div>
        );
    }
}

export default Domain;
