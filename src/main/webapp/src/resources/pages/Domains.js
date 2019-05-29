import React from "react";
import "../styles/Domains.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import ReactTable from "react-table";
import "react-table/react-table.css";

class Domains extends React.Component {

    render() {
        return (
            <div>
                <Table ref="supTable"/>
                <Form onSubmit = {this.onFormSubmit}/>
            </div>
        );
    }

    onFormSubmit = () => {
        this.refs.supTable.refreshDomainsTable();
        console.log("Form submited");
    }

}

const API = "http://localhost:8001/domains/all";

class Table extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            data : [],
            selected: {}
        };
    }

    componentDidMount() {
        this.refreshDomainsTable();
    }

    deleteDomains = (id) => {
        let self = this;
        let URI = 'http://localhost:8001/domains/delete/' + id;
        fetch(URI)
            .then(function(response) {
                return response;
            }).then(function(data) {
            self.refreshDomainsTable();
        });
    }

    toggleRow(zoneId) {
        console.log(this.state.selected);
        const newSelected = Object.assign( {}, this.state.selected);
        newSelected[zoneId] = !this.state.selected[zoneId];
        this.setState({
            selected: newSelected,
        });
    }

    renderTable() {
        const columns = [
            {
                Header : "ID",
                accessor: 'domain.id',
            },
            {
                Header : "Domain Name",
                accessor: 'domain.name',
            },
            {
                Header : "Domain Type",
                accessor: 'domain.type',
            },
            {
                Header : "Domain Master",
                accessor: 'domain.master',
            },
            {
                Header : "Owner",
                accessor: "owner",
            },
            {
                Header : "Comment",
                accessor: "comment",
            },

        ];

        return (
            <ReactTable
                data={this.state.data}
                columns={columns}
                defaultSorted={ [ { id : "id", desc: true} ] }
            />
        );
    }

    refreshDomainsTable() {
        fetch(API)
            .then(response => {
                if (response.ok) {
                    return response;
                }
                throw Error(response.status);
            })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                this.setState({data: data, selected: {}});
            })
            .catch(error => console.log(error + " coś nie tak"));
    }

    render() {
        return this.renderTable();
    }
}

class Form extends React.Component {

    constructor(props) {
        super(props);

        this.state = {

            domains : [

                {
                    id : '',
                    domain: {
                        id: '',
                        name: '',
                        master: '',
                        type: ''
                    },
                    owner : '',
                    comment : ''
                }

            ]
        };

        this.handleNewDomains = this.handleNewDomains.bind(this);
    }

    addZone = (event) => {
        event.preventDefault();
        this.setState((previousState) => ( {
            domains : [... previousState.domains,
                {
                    id : '',
                    domain: {
                        id: '',
                        name: '',
                        master: '',
                        type: ''
                    },
                    owner : '',
                    comment : ''
                }
            ],
        }));
    }

    handleChange = (e) => {
        let domains = [...this.state.domains];
        if(e.target.className == 'domainname'){
            domains[e.target.dataset.id]['domain'].name = e.target.value;
        } else if (e.target.className == 'domainmaster'){
            domains[e.target.dataset.id]['domain'].master = e.target.value;
        } else if (e.target.className == 'domaintype'){
            domains[e.target.dataset.id]['domain'].type = e.target.value;
        } else if (e.target.className == 'domainid'){
            domains[e.target.dataset.id]['domain'].id = e.target.value;
        }else{
            domains[e.target.dataset.id][e.target.className] = e.target.value;
        }
        this.setState({domains}, () => console.log(this.state.domains));
    }

    render() {
        let {domains} = this.state;
        return (
            <form className="form-signin" style = {{width: '100%'}}>
                <table className = "table" style = {{width: '100%'}} >
                    <tbody>
                    {
                        domains.map((value, idx) => {
                            let id = 'id-${idx}',
                                domainId= 'domainid-${idx}',
                                domainName= 'domainname-${idx}',
                                domainType= 'domaintype-${idx}',
                                domainMaster= 'domainmaster-${idx}',
                                owner= 'owner-${idx}',
                                comment= 'comment-${idx}';
                            return (
                                <tr key={idx}>
                                    <td>
                                        <input
                                            type = "text"
                                            name = {domainName}
                                            data-id = {idx}
                                            id = {domainName}
                                            value={domains[idx].domain.name}
                                            className="domainname"
                                            placeholder="Domain Name"
                                            onChange = {this.handleChange}
                                            style={{flex: 1}}
                                        /></td>
                                    <td>
                                        <input
                                            type = "text"
                                            name = {domainType}
                                            data-id = {idx}
                                            id = {domainType}
                                            value={domains[idx].domain.type}
                                            className="domaintype"
                                            placeholder="Domain Type"
                                            onChange = {this.handleChange}
                                            style={{flex: 1}}
                                        /></td>
                                    <td>
                                        <input
                                            type = "text"
                                            name = {domainMaster}
                                            data-id = {idx}
                                            id = {domainMaster}
                                            value={domains[idx].domain.master}
                                            className="domainmaster"
                                            placeholder="Domain Master"
                                            onChange = {this.handleChange}
                                            style={{flex: 1}}
                                        /></td>
                                    <td>
                                        <input
                                            type = "text"
                                            name = {owner}
                                            data-id = {idx}
                                            id = {owner}
                                            value={domains[idx].owner}
                                            className="owner"
                                            placeholder="Owner"
                                            onChange = {this.handleChange}
                                            style={{flex: 1}}
                                        /></td>
                                    <td>
                                        <input
                                            type = "text"
                                            name = {comment}
                                            data-id = {idx}
                                            id = {comment}
                                            value={domains[idx].comment}
                                            className="comment"
                                            placeholder="Comment"
                                            onChange = {this.handleChange}
                                            style={{flex: 1}}
                                        /></td>
                                </tr>
                            )
                        })
                    }
                    <tr>
                        <td>
                            <button className="btn btn-lg btn-primary btn-block" onClick = {this.addZone}> Add new zone </button>
                        </td>
                        <td>
                            <button className="btn btn-lg btn-primary btn-block" onClick={(e) => this.handleNewDomains(e)}>Submit</button>
                        </td>
                        <td></td><td></td>
                    </tr>
                    </tbody>
                </table>
            </form>
        )
    }

    handleNewDomains(event) {
        event.preventDefault();
        fetch('http://localhost:8001/domains', {
            method: 'post',
            body: JSON.stringify(this.state.domains),
            headers: {'Content-Type': 'application/json'}
        }).then((response) => {
            return response;
        }).then((data) => {
            console.log('Created domains', data);
            this.setState(this.state = {

                domains : [

                    {
                        id : '',
                        domain: {
                            id: '',
                            name: '',
                            master: '',
                            type: ''
                        },
                        owner : '',
                        comment : ''
                    }

                ]
            });
            this.props.onSubmit();
        });
    }

}

export default Domains;
