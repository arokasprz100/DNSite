import React from "react";
import "../styles/Administration.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import ReactTable from "react-table";
import "react-table/react-table.css";

class Administration extends React.Component {

    constructor(props) {
        super(props);

        console.log("Constructor");

        this.state = {
            data : []
        };
    }

    refreshTable() {
        console.log(this.state.data);
        fetch('http://localhost:8001/administration/user-confirm/all')
            .then(response => {
                if (response.ok) {
                    return response;
                }
            throw Error(response.status);
            })
            .then(response => response.json())
            .then(data => {
                this.setState({data: data, selected: {}});
            })
        .catch(error => console.log(error + " coś nie tak"));
    }

    componentDidMount() {
        console.log("Mount");
        console.log(this.state.data);
        this.refreshTable();
    }

    acceptUser = (userId) => {
        let self = this;
        let URI = 'http://localhost:8001/administration/user-confirm/' + userId  + '/accept';
        fetch(URI)
        .then(function(response) {
            return response;
        }).then(function(data) {
            self.refreshTable();
        });
    }

    rejectUser = (userId) => {
        let self = this;
        let URI = 'http://localhost:8001/administration/user-confirm/' + userId  + '/reject';
        fetch(URI)
        .then(function(response) {
            return response;
        }).then(function(data) {
            self.refreshTable();
        });

    }

    renderTable = ()  => {
        const columns = [
        {
            Header : "Username",
            accessor: 'username',
        },
        {
            Header : "Email",
            accessor: 'email',
        },
        {
            Header : "Accept",
            accessor: "",
            Cell : ({original}) => {
                return (
                    <button onClick = { () => {this.acceptUser(original.id)}} > Accept </button>
                );
            },
            sortable: false
        },
        {
            Header : "Reject",
            accessor : "",
            Cell : ({original}) => {
                return (
                    <button onClick = { () => {this.rejectUser(original.id)}} > Reject </button>
                );
            },
            sortable: false
        }
        ];

        return (
            <ReactTable
                data={this.state.data}
                columns={columns}
                defaultSorted={ [ { id : "username", desc: true} ] }
            />
        );
    }

    render() {
        return this.renderTable();
    }

}

export default Administration;
