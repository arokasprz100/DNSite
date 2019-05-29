import React, {Component} from "react";
import "../styles/Supermasters.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import ReactTable from "react-table";
import "react-table/react-table.css";

class Supermasters extends Component {

    render() {
        return (
            <div>
                <Table ref="supTable"/>
            </div>
        );
    }
}

const API = "http://localhost:8001/supermasters/all";

class Table extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            data : [],
            selected: {},
            selectAll : 0,
            currentIndex : 0,
            recordsToDelete : [],
        };
    }

    componentDidMount() {
        this.refreshSupermastersTable();
    }

    deleteSupermaster = (original) => {
        var array = [...this.state.data];
        var index = array.indexOf(original)
        if (index !== -1) {
            array.splice(index, 1);
            this.setState({data: array});
        }

        if (original.committed === true) {
            this.setState( (previousState) => ( {
                recordsToDelete : [... previousState.recordsToDelete,
                    original]
            }));
        }
    }

    toggleRow(supermasterTableIndex) {
        const newSelected = Object.assign( {}, this.state.selected);
        newSelected[supermasterTableIndex] = !this.state.selected[supermasterTableIndex];
        this.setState({
            selected: newSelected,
            selectAll: 2
        });
    }

    toggleSelectAll() {
        let newSelected = {};

        if (this.state.selectAll === 0) {
            this.state.data.forEach(x => {
                newSelected[x.tableIndex] = true;
            });
        }

        this.setState({
            selected: newSelected,
            selectAll: this.state.selectAll === 0 ? 1 : 0
        });
    }

    addSupermaster = (event) => {
        let currentTableIndex = this.state.currentIndex;

        this.setState((previousState) => ( {
            data : [...previousState.data,
                {
                    supermasterId : {
                        ip : { address : '' },
                        nameserver : ''
                    },
                    account: '',
                    tableIndex : currentTableIndex,
                    committed : false
                }
            ],
            currentIndex : currentTableIndex + 1
        }));
    }

    getSelectedRows = () => {
        return Object.keys(
            Object.fromEntries(
                Object.entries(this.state.selected).filter(([k,v]) => v === true)
            )
        ).map(Number);
    }

    getSelectedItems = (selectedRows) => {
        return JSON.parse(JSON.stringify(this.state.data.filter(item => {
            return (selectedRows.indexOf(item.tableIndex) != -1);
        })));
    }

    deleteMultipleSupermasters = () => {
        const selectedRows = this.getSelectedRows();
        const toDelete = this.getSelectedItems(selectedRows).filter(item => item.committed == true);
        const toKeep = this.state.data.filter(item => {
            return (selectedRows.indexOf(item.tableIndex) === -1);
        });

        this.setState( (previousState) => ( {
            recordsToDelete : [... previousState.recordsToDelete, ...toDelete],
            data : toKeep,
            selected: {},
            selectAll: 0
        }));
    }

    renderEditableWhenNotCommitted = cellInfo => {
        const data = [...this.state.data];

        if (data[cellInfo.index].committed === true)
        {
            return (
                <div
                    dangerouslySetInnerHTML={{
                        __html: (cellInfo.column.id === 'supermasterId.nameserver') ?
                            this.state.data[cellInfo.index].supermasterId.nameserver :
                            this.state.data[cellInfo.index].supermasterId.ip.address
                    }}
                />
            );
        }
        else
        {
            return (
                <div
                    style={{ backgroundColor: "#fafafa" }}
                    contentEditable
                    suppressContentEditableWarning
                    onBlur={e => {
                        const data = [...this.state.data];
                        (cellInfo.column.id === 'supermasterId.nameserver') ?
                            data[cellInfo.index].supermasterId.nameserver  = e.target.innerHTML :
                            data[cellInfo.index].supermasterId.ip.address  = e.target.innerHTML;

                        this.setState({ data });
                    }}
                    dangerouslySetInnerHTML={{
                        __html: (cellInfo.column.id === 'supermasterId.nameserver') ?
                            this.state.data[cellInfo.index].supermasterId.nameserver :
                            this.state.data[cellInfo.index].supermasterId.ip.address
                    }}
                />
            );
        }

    };

    renderEditable = cellInfo => {
        return (
            <div
                style={{ backgroundColor: "#fafafa" }}
                contentEditable
                suppressContentEditableWarning
                onBlur={e => {
                    const data = [...this.state.data];
                    data[cellInfo.index][cellInfo.column.id] = e.target.innerHTML;
                    this.setState({ data });
                }}
                dangerouslySetInnerHTML={{
                    __html: this.state.data[cellInfo.index][cellInfo.column.id]
                }}
            />
        );
    };

    deleteRecordsFromDb = () => {
        fetch('http://localhost:8001/supermasters/delete', {
            method: 'post',
            body: JSON.stringify(this.state.recordsToDelete),
            headers: {'Content-Type': 'application/json'}
        }).then((response) => {
            return response;
        }).then((data) => {
            console.log('Complete', data);
            this.setState({recordsToDelete : []});
        });
    }

    commitChanges = () => {
        console.log(JSON.stringify(this.state.data));
        fetch('http://localhost:8001/supermasters/commit', {
            method: 'post',
            body: JSON.stringify(this.state.data),
            headers: {'Content-Type': 'application/json'}
        }).then((response) => {
            return response;
        }).then((data) => {
            console.log('Complete', data);
            this.deleteRecordsFromDb();
        });
    }

    renderTable() {
        const columns = [
        {
            Header : x => {
                return (
                    <input
                        type="checkbox"
                        checked={this.state.selectAll === 1}
                        ref={input => {
                            if (input) {
                                input.indeterminate = this.state.selectAll === 2;
                            }
                        }}
                        onChange={() => this.toggleSelectAll()}
                    />
                );
            },
            id : "checkbox",
            accessor: "",
            Cell: ({original}) => {
                return (
                    <input
                        type = "checkbox"
                        className="checkbox"
                        checked={this.state.selected[original.tableIndex] === true}
                        onChange = { () => this.toggleRow(original.tableIndex) }
                    />
                );
            },
            sortable: false,
            width: 45
        },
        {
            Header : "IP",
            accessor: 'supermasterId.ip.address',
            Cell: this.renderEditableWhenNotCommitted
        },
        {
            Header : "Nameserver",
            accessor: 'supermasterId.nameserver',
            Cell: this.renderEditableWhenNotCommitted
        },
        {
            Header : "Account",
            accessor: "account",
            Cell: this.renderEditable
        },
        {
            Header : "Delete",
            accessor : "",
            Cell : ({original}) => {
                return (
                    <button onClick={ () => {this.deleteSupermaster(original)}}> Delete </button>
                );
            },
            sortable: false,
            Footer : (
                <button onClick= { () => this.deleteMultipleSupermasters() } > Delete selected </button>
            )
        }

        ];

        return (
            <div>
                <ReactTable
                    data={this.state.data}
                    columns={columns}
                    defaultSorted={ [ { id : "account", desc: true} ] }
                />
                <button onClick={ () => this.addSupermaster() }> Add supermaster </button>
                <button onClick={ () => this.commitChanges() }> Commit changes </button>
            </div>

        );
    }

    refreshSupermastersTable() {
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
                let currentTableIndex = this.state.currentIndex;
                data.map((supermaster, index) => {
                    supermaster.tableIndex = currentTableIndex;
                    supermaster.committed = true;
                    currentTableIndex = currentTableIndex + 1;
                });
                this.setState({data: data, selected: {}, currentIndex : currentTableIndex});
            })
        .catch(error => console.log(error + " coś nie tak"));
    }

    render() {
        return this.renderTable();
    }
}


export default Supermasters;
