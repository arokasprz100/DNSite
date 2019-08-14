import React from "react"
import "../styles/Domains.css"
import Button from "react-bootstrap/Button"
import Form from 'react-bootstrap/Form'
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import {Tab, Tabs} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import ReusableTable from "../components/ReusableTable.js"

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
                        <DomainRecordsTable
                            domainId = {this.props.match.params.id}
                        />
                    </Tab>
                    <Tab eventKey="comments" title="Comments">
                        <DomainCommentsTable
                            domainId = {this.props.match.params.id}
                        />
                    </Tab>
                </Tabs>
            </div>
        );
    }
}

class DomainForm extends React.Component{

    constructor(props) {
        super(props);

        this.state= {
            name : '',
            type: '',
            notifiedSerial: '',
            master : '',
            lastCheck: '',
            id : '',
            account: '',

            types: [],
            copy: {},

            errorMessages: []
        };

        this.urlId = this.props.urlId;
    }

    componentDidMount() {
        this.fetchTypes();
    }

    handleChange = e => {
        if (e.target.name === "type" && e.target.value !== "SLAVE") {
            this.setState({[e.target.name]: e.target.value, master: ""});
        }
        else {
            this.setState({[e.target.name]: e.target.value});
        }
    }

    handleSubmit = e => {
        e.preventDefault();
        let domainExtension = [{
            id: this.state.id,
            name: this.state.name,
            type: this.state.type,
            notifiedSerial: this.state.notifiedSerial,
            master: this.state.master,
            lastCheck: this.state.lastCheck
        }]
        fetch(this.props.resourcesURLBase + 'commit', {
            method: 'post',
            body: JSON.stringify(domainExtension),
            headers: {'Content-Type': 'application/json'}
        }).then( (response) => response.json()
        ).then((data) => {
            if (data.length === 0) {
                console.log('Complete', data);
                this.refreshDomainForm();
            }
            else {
                console.log(data);
                this.setState({ errorMessages : data });
            }

        });
    }

    getErrorMessages = (propertyName) => {
        return this.state.errorMessages.filter( (element) => {
            return element.field === propertyName;
        }).map( (element) => {
            return (<span>{element.message}</span>);
        });
    }


    render() {
        return (
            <Form style = {{display: 'flex', justifyContent: 'center', flexDirection: 'row',}} onSubmit={e => this.handleSubmit(e)}>
                <Col>
                    <Row style = {{display: 'flex', justifyContent: 'center', flexDirection: 'row',}} >
                        <Form.Group controlId = "formGridName">
                            <Form.Label>Name</Form.Label>
                            <Form.Text className = "text-muted">{this.state.copy.name}</Form.Text>
                            <Form.Control name = "name" placeholder = {this.state.name} onChange = {this.handleChange} ref = "nameForm"/>
                            <Form.Text>{ this.getErrorMessages("name") }</Form.Text>
                        </Form.Group>
                    </Row>
                    <Row style = {{display: 'flex', justifyContent: 'center', flexDirection: 'row',}} >
                        <Form.Group controlId = "formGridType">
                            <Form.Label>Type</Form.Label>
                            <Form.Text className = "text-muted">{this.state.copy.type}</Form.Text>
                            <Form.Control name = "type" as = "select"  onChange = {this.handleChange} ref = "typeForm">
                                {this.state.types.map((type) => {
                                    return (<option>{type}</option>)
                                })}
                            </Form.Control>
                            <Form.Text>{ this.getErrorMessages("type") }</Form.Text>
                        </Form.Group>
                    </Row>
                    <Row style = {{display: 'flex', justifyContent: 'center', flexDirection: 'row',}} >
                        <Form.Group controlId="formGridType">
                            <Form.Label>Notified Serial</Form.Label>
                            <Form.Text className="text-muted">{this.state.copy.notifiedSerial}</Form.Text>
                            <Form.Control name="notifiedSerial" placeholder={this.state.notifiedSerial} disabled={true} ref="serialForm"/>
                            <Form.Text>{ this.getErrorMessages("notifiedSerial") }</Form.Text>
                        </Form.Group>
                    </Row>
                    <Row style = {{display: 'flex', justifyContent: 'center', flexDirection: 'row',}} >
                        <Form.Group controlId="formGridMaster">
                            <Form.Label>Master</Form.Label>
                            <Form.Text className="text-muted">{this.state.copy.master}</Form.Text>
                            <Form.Control name="master" placeholder={this.state.master} disabled = {this.state.type !== "SLAVE"} onChange = {this.handleChange} ref="masterForm"/>
                            <Form.Text>{ this.getErrorMessages("master") }</Form.Text>
                        </Form.Group>
                    </Row>
                    <Row style = {{display: 'flex', justifyContent: 'center', flexDirection: 'row',}} >
                        <Form.Group controlId="formGridLastCheck">
                            <Form.Label>Last Check</Form.Label>
                            <Form.Text className="text-muted">{this.state.copy.lastCheck}</Form.Text>
                            <Form.Control name="lastCheck" placeholder={this.state.lastCheck} disabled={true} ref="checkForm"/>
                            <Form.Text>{ this.getErrorMessages("lastCheck") }</Form.Text>
                        </Form.Group>
                    </Row>

                    <Button variant="primary" type="submit"> Submit </Button>
                </Col>
            </Form>
        )
    }

    refreshDomainForm() {
        fetch(this.props.resourcesURLBase + this.urlId)
            .then(response => {
                if (response.ok) {
                    return response;
                }
                throw Error(response.status);
            })
            .then(response => response.json())
            .then(data => {
                this.setState((prev) => ({
                    id : data.id,
                    name : data.name,
                    master : data.master,
                    lastCheck: data.lastCheck,
                    type: data.type,
                    notifiedSerial: data.notifiedSerial,
                    account: data.account,
                    copy : {
                        id : data.id,
                        name : data.name,
                        master : data.master,
                        lastCheck: data.lastCheck,
                        type: data.type,
                        notifiedSerial: data.notifiedSerial,
                        account: data.account
                    },
                    errorMessages: []
                }));
            })
            .catch(error => console.log(error + " coś nie tak"));
    }

    fetchTypes() {
        fetch(this.props.resourcesURLBase + "types")
            .then(response => {
                if (response.ok) {
                    return response;
                }
                throw Error(response.status);
            })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                this.setState((prev) => ({
                    types: data
                }));
                this.refreshDomainForm();
            })
            .catch(error => console.log(error + " coś nie tak"));
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
        let emptyDataExample = { id : '', domainId : this.props.domainId, name : '', type : '', content : '', ttl : '' };
        const columns = [
            { Header : "ID", accessor : "id", type: "none" },
            { Header : "Name", accessor : "name", type: "text" },
            { Header : "Type", accessor : "type", type: "select" },
            { Header : "Content", accessor : "content", type: "text" },
            { Header : "TTL", accessor : "ttl", type: "number" },
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
        let emptyDataExample = { id : '', domainId : this.props.domainId, name : '', type : '', modifiedAt : '', comment : '' };
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
