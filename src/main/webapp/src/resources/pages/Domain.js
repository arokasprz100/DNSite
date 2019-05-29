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

const API = "http://localhost:8001/domain/";

class DomainForm extends React.Component{

    constructor(props) {
        super(props);

        this.state= {
            domain: {
                name : '',
                master : '',
                lastCheck: '',
                type: '',
                notifiedSerial: '',
                account: '',
                owner: '',
                comment: ''
            }
        };
        this.urlId = this.props.urlId;
        this.handleDomainInfo = this.handleDomainInfo.bind(this);
    }

    handleChange = (e) => {
        let domain = [...this.state.domains];
        domain[e.target.dataset.id][e.target.className] = e.target.value;
        this.setState({domain}, () => console.log(this.state.domain));
    }

    handleDomainInfo(event) {
        event.preventDefault();
    }

    render() {
        let {domain} = this.state;
        return (
            <Form
                // onSubmit={e => this.handleSubmit(e)}
            >
                {this.urlId}
                <Row>
                    <Col>
                        {/*<Form.Group controlId="formGridName">*/}
                            <Form.Label>Name</Form.Label>
                            <Form.Control placeholder={domain.name} onChange = {this.handleChange}/>
                        {/*</Form.Group>*/}
                    </Col>
                    <Col>
                        {/*<Form.Group controlId="formGridType">*/}
                            <Form.Label>Type</Form.Label>
                            <Form.Control placeholder={domain.type} onChange = {this.handleChange}/>
                        {/*</Form.Group>*/}
                    </Col>
                </Row>

                <Row>
                    <Col>
                        <Form.Group controlId="formGridType">
                            <Form.Label>Type</Form.Label>
                            <Form.Control placeholder={domain.type} onChange = {this.handleChange}/>
                        </Form.Group>
                    </Col>

                    <Col>
                        <Form.Group controlId="formGridMaster">
                            <Form.Label>Master</Form.Label>
                            <Form.Control placeholder={domain.master} onChange = {this.handleChange}/>
                        </Form.Group>
                    </Col>

                    <Col>
                        <Form.Group controlId="formGridLastCheck">
                            <Form.Label>Last Check</Form.Label>
                            <Form.Control placeholder={domain.lastCheck} onChange = {this.handleChange}/>
                        </Form.Group>
                    </Col>
                </Row>

                <Row>
                    <Col>
                        <Form.Group controlId="formGridComment">
                            <Form.Label>Comemnt</Form.Label>
                            <Form.Control placeholder={domain.comment} onChange = {this.handleChange}/>
                        </Form.Group>
                    </Col>
                </Row>

                <Button variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        )
    }
}

export default Domain;
