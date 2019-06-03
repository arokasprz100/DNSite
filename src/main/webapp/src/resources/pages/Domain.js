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

class DomainForm extends React.Component{

    constructor(props) {
        super(props);

        this.state= {
            id : '',
            name : '',
            master : '',
            lastCheck: '',
            type: '',
            notifiedSerial: '',
            account: '',
            owner: '',
            comment: ''
        }
        this.urlId = this.props.urlId;
    }

    componentDidMount() {
        this.refreshDomainForm();
    }

    handleDomainInfo(event) {
        event.preventDefault();
    }

    handleChange = e => {
        this.setState({[e.target.name]: e.target.value});
    };

    render() {
        return (
            <Form
                // onSubmit={e => this.handleSubmit(e)}
            >
                <Row>
                    <Col>
                        <Form.Group controlId="formGridName">
                            <Form.Label>Name</Form.Label>
                            <Form.Control placeholder={this.state.name} onChange = {this.handleChange}/>
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group controlId="formGridType">
                            <Form.Label>Type</Form.Label>
                            <Form.Control placeholder={this.state.type} onChange = {this.handleChange}/>
                        </Form.Group>
                    </Col>
                </Row>

                <Row>
                    <Col>
                        <Form.Group controlId="formGridType">
                            <Form.Label>Notified Serial</Form.Label>
                            <Form.Control placeholder={this.state.notifiedSerial} onChange = {this.handleChange}/>
                        </Form.Group>
                    </Col>

                    <Col>
                        <Form.Group controlId="formGridMaster">
                            <Form.Label>Master</Form.Label>
                            <Form.Control placeholder={this.state.master} onChange = {this.handleChange}/>
                        </Form.Group>
                    </Col>

                    <Col>
                        <Form.Group controlId="formGridLastCheck">
                            <Form.Label>Last Check</Form.Label>
                            <Form.Control placeholder={this.state.lastCheck} onChange = {this.handleChange}/>
                        </Form.Group>
                    </Col>
                </Row>

                <Row>
                    <Col>
                        <Form.Group controlId="formGridComment">
                            <Form.Label>Comemnt</Form.Label>
                            <Form.Control placeholder={this.state.comment} onChange = {this.handleChange}/>
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
                    comment: data.comment
                }));
            })
            .catch(error => console.log(error + " coś nie tak"));
    }
}

export default Domain;
