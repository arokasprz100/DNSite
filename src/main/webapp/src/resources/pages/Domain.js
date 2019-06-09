import React from "react";
import "../styles/Domains.css";
import Button from "react-bootstrap/Button";
import Form from 'react-bootstrap/Form'
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import 'bootstrap/dist/css/bootstrap.min.css';
import "react-table/react-table.css";

class Domain extends React.Component {

    render() {
        return (
            <div>
                <DomainForm urlId = {this.props.match.params.id}/>
            </div>
        );
    }
}

const API = "http://localhost:8001/domains/";
const TypeAPI = API+"types";



class DomainForm extends React.Component{

    constructor(props) {
        super(props);

        this.state= {
            name : '',
            type: '',
            notifiedSerial: '',
            master : '',
            lastCheck: '',
            comment: '',
            domainId: '',

            id : '',
            account: '',
            owner: '',
            types: [],
            copy: {}
        };

        this.urlId = this.props.urlId;
    }

    componentDidMount() {
        this.fetchTypes();
    }

    handleDomainInfo(event) {
        event.preventDefault();
    }

    handleChange = e => {
        this.setState({[e.target.name]: e.target.value});
    }

    handleSubmit = e => {
        e.preventDefault();
        let domainExtension = [{
            owner: this.state.owner,
            comment: this.state.comment,
            id: this.state.id,
            domain: {
                id: this.state.domainId,
                name: this.state.name,
                type: this.state.type,
                notifiedSerial: this.state.notifiedSerial,
                master: this.state.master,
                lastCheck: this.state.lastCheck
            }
        }]
        fetch('http://localhost:8001/domains', {
            method: 'post',
            body: JSON.stringify(domainExtension),
            headers: {'Content-Type': 'application/json'}
        }).then((response) => {
            return response;
        }).then((data) => {
            console.log('Complete', data);
            this.refreshDomainForm();
        });
    }


    render() {
        return (
            <Form onSubmit={e => this.handleSubmit(e)}>
                <Row>
                    <Col>
                        <Form.Group controlId="formGridName">
                            <Form.Label>Name</Form.Label>
                            <Form.Control name="name" placeholder={this.state.name} onChange = {this.handleChange} ref="nameForm"/>
                            <Form.Text className="text-muted">{this.state.copy.name}</Form.Text>
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group controlId="formGridType">
                            <Form.Label>Type</Form.Label>
                            <Form.Control name="type" as="select"  onChange = {this.handleChange} ref="typeForm">
                                {this.state.types.map((type) => {
                                    return (<option>{type}</option>)
                                })}
                            </Form.Control>
                            <Form.Text className="text-muted">{this.state.copy.type}</Form.Text>
                        </Form.Group>
                    </Col>
                </Row>

                <Row>
                    <Col>
                        <Form.Group controlId="formGridType">
                            <Form.Label>Notified Serial</Form.Label>
                            <Form.Control name="notifiedSerial" placeholder={this.state.notifiedSerial} disabled={true} ref="serialForm"/>
                            <Form.Text className="text-muted">{this.state.copy.notifiedSerial}</Form.Text>
                        </Form.Group>
                    </Col>

                    <Col>
                        <Form.Group controlId="formGridMaster">
                            <Form.Label>Master</Form.Label>
                            <Form.Control name="master" placeholder={this.state.master} disabled = {this.state.type !== "SLAVE"} onChange = {this.handleChange} ref="masterForm"/>
                            <Form.Text className="text-muted">{this.state.copy.master}</Form.Text>
                        </Form.Group>
                    </Col>

                    <Col>
                        <Form.Group controlId="formGridLastCheck">
                            <Form.Label>Last Check</Form.Label>
                            <Form.Control name="lastCheck" placeholder={this.state.lastCheck} disabled={true} ref="checkForm"/>
                            <Form.Text className="text-muted">{this.state.copy.lastCheck}</Form.Text>
                        </Form.Group>
                    </Col>
                </Row>

                <Row>
                    <Col>
                        <Form.Group controlId="formGridComment">
                            <Form.Label>Comment</Form.Label>
                            <Form.Control name="comment" placeholder={this.state.comment} onChange = {this.handleChange} ref="commentForm"/>
                            <Form.Text className="text-muted">{this.state.copy.comment}</Form.Text>
                        </Form.Group>
                    </Col>
                </Row>

                <Button variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        )
    }

    refreshDomainForm() {
        fetch(API+this.urlId)
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
                    id : data.id,
                    name : data.domain.name,
                    master : data.domain.master,
                    lastCheck: data.domain.lastCheck,
                    type: data.domain.type,
                    notifiedSerial: data.domain.notifiedSerial,
                    account: data.domain.account,
                    owner: data.owner,
                    comment: data.comment,
                    copy : {
                        id : data.id,
                        name : data.domain.name,
                        master : data.domain.master,
                        lastCheck: data.domain.lastCheck,
                        type: data.domain.type,
                        notifiedSerial: data.domain.notifiedSerial,
                        account: data.domain.account,
                        owner: data.owner,
                        comment: data.comment
                    }
                }));
            })
            .catch(error => console.log(error + " coś nie tak"));
    }

    fetchTypes() {
        fetch(TypeAPI)
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
                    id : prev.id,
                    name : prev.name,
                    master : prev.master,
                    lastCheck: prev.lastCheck,
                    type: prev.type,
                    notifiedSerial: prev.notifiedSerial,
                    account: prev.account,
                    owner: prev.owner,
                    comment: prev.comment,
                    types: data
                }));
                this.refreshDomainForm();
            })
            .catch(error => console.log(error + " coś nie tak"));
    }
}

export default Domain;
