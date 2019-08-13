import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import ReactTable from "react-table";
import "react-table/react-table.css";

class HistoryPanel extends React.Component {
    constructor(props) {
        super(props);

        this.state = { data: [] };
    }

    refreshTable() {
        fetch("http://localhost:8001/history/all")
            .then(response => {
                if (response.ok) { return response; }
                throw Error(response.status);
            })
            .then(response => response.json())
            .then(data => { this.setState({ data: data, selected: {} }); })
            .catch(error => console.log("Following error occurred: " + error));
    }

    componentDidMount() {
        this.refreshTable();
    }

    renderTable = () => {
        const columns = [
            {
                Header: "Id",
                accessor: "id"
            },
            {
                Header: "Model",
                accessor: "model"
            },
            {
                Header: "User",
                accessor: "userName"
            },
            {
                Header: "Action",
                accessor: "action"
            },
            {
                Header: "Created",
                accessor: "created"
            },
            {
                Header: "Updated",
                accessor: "updated"
            }

        ];

        return (
            <ReactTable
                data={this.state.data}
                columns={columns}
                defaultSorted={[{ id: "username", desc: true }]}
            />
        );
    };

    render() {
        return this.renderTable();
    }
}

export default HistoryPanel;
