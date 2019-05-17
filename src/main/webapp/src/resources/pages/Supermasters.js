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
                <Form onSubmit = {this.onFormSubmit}/>
            </div>
        );
    }

    onFormSubmit = () => {
        this.refs.supTable.refreshSupermastersTable();
        console.log("Form submited");
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
        };
    }

    componentDidMount() {
        this.refreshSupermastersTable();
    }

    deleteSupermaster = (ip, nameserver) => {
        let self = this;
        let URI = 'http://localhost:8001/supermasters/delete/' + ip.address + '/' + nameserver;
        fetch(URI)
        .then(function(response) {
            return response;
        }).then(function(data) {
            self.refreshSupermastersTable();
        });
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
            data : [... previousState.data,
                {
                    supermasterId : {
                        ip : {address : '' },
                        nameserver : ''
                    },
                    account: '',
                    tableIndex : currentTableIndex,
                    commited : false
                },
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
        const toDelete = this.getSelectedItems(selectedRows).filter(item => item.commited == true);
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
            Cell: this.renderEditable
        },
        {
            Header : "Nameserver",
            accessor: 'supermasterId.nameserver',
            Cell: this.renderEditable
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
                    <button onClick={ () => {this.deleteSupermaster(original.supermasterId.ip, original.supermasterId.nameserver)}}> Delete </button>
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
                let currentTableIndex = this.state.currentIndex;
                data.map((supermaster, index) => {
                    supermaster.tableIndex = currentTableIndex;
                    supermaster.commited = true;
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

class Form extends React.Component {

    constructor(props) {
        super(props);

        this.state = {

            supermasters : [

                {
                    supermasterId : {
                        ip : {address : '' },
                        nameserver : ''
                    },
                    account: ''
                }

            ]
        };

        this.handleNewSupermasters = this.handleNewSupermasters.bind(this);
    }

    addSupermaster = (event) => {
        event.preventDefault();
        this.setState((previousState) => ( {
            supermasters : [... previousState.supermasters,
                {
                    supermasterId : {
                        ip : {address : '' },
                        nameserver : ''
                    },
                    account: ''
                }
            ],
        }));
    }

    handleChange = (e) => {
        let supermasters = [...this.state.supermasters];
        if (e.target.className == 'ip') {
            var supermasterId = supermasters[e.target.dataset.id]['supermasterId'];
            supermasterId.ip = e.target.value;
        }
        else if (e.target.className == 'nameserver') {
            var supermasterId = supermasters[e.target.dataset.id]['supermasterId'];
            supermasterId.nameserver = e.target.value;
        }
        else if (e.target.className == 'account') {
            supermasters[e.target.dataset.id]['account'] = e.target.value;
        }
        this.setState({supermasters}, () => console.log(this.state.supermasters));
    }

    render() {
        let {supermasters} = this.state;
        return (
            <form className="form-signin" style = {{width: '100%'}}>
            <table className = "table" style = {{width: '100%'}} >
            <tbody>
            {
                supermasters.map((value, idx) => {
                    let supermasterIpId = 'ip-${idx}',
                        supermasterNameserverId = 'age-${idx}',
                        supermasterAccountId = 'account-${idx}';
                    return (
                        <tr key={idx}>
                            <td>
                            <input
                                type = "text"
                                name = {supermasterIpId}
                                data-id = {idx}
                                id = {supermasterIpId}
                                value={supermasters[idx].supermasterId.ip.address}
                                className="ip"
                                placeholder="IP [required]"
                                onChange = {this.handleChange}
                            /></td>
                            <td>
                            <input
                                type = "text"
                                name = {supermasterNameserverId}
                                data-id = {idx}
                                id = {supermasterNameserverId}
                                value={supermasters[idx].supermasterId.nameserver}
                                className="nameserver"
                                placeholder="Nameserver [required]"
                                onChange = {this.handleChange}
                            /></td>
                            <td>
                            <input
                                type = "text"
                                name = {supermasterAccountId}
                                data-id = {idx}
                                id = {supermasterAccountId}
                                value={supermasters[idx].account}
                                className="account"
                                placeholder="Account [required]"
                                onChange = {this.handleChange}
                            /></td>
                        </tr>
                    )
                })
            }
            <tr>
                <td>
                    <button className="btn btn-lg btn-primary btn-block" onClick = {this.addSupermaster}> Add new supermaster </button>
                </td>
                <td>
                    <button className="btn btn-lg btn-primary btn-block" onClick={(e) => this.handleNewSupermasters(e)}>Submit</button>
                </td>
                <td></td><td></td>
            </tr>
            </tbody>
            </table>
            </form>
        )
    }

    handleNewSupermasters(event) {
        event.preventDefault();
        fetch('http://localhost:8001/supermasters', {
            method: 'post',
            body: JSON.stringify(this.state.supermasters),
            headers: {'Content-Type': 'application/json'}
        }).then((response) => {
            return response;
        }).then((data) => {
            console.log('Created supermaster', data);
            this.setState({supermasters : [
                {
                    supermasterId : {
                        ip : {address : ''},
                        nameserver : ''
                    },
                    account: ''
                }
            ]});
            this.props.onSubmit();
        });
    }

}

export default Supermasters;
