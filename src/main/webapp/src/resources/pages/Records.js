import React, {Component} from "react";
import "../styles/Supermasters.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import ReactTable from "react-table";
import "react-table/react-table.css";

const API = "http://localhost:8001/records/all";

class Records extends Component {

    render() {
        return (
            <div>
                <Table ref="supTable"/>
            </div>
        );
    }
}

class Table extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            records : [],
            selected : {},
            toDelete : [],
            editedContent : {},

            currentIndex : 0,
            selectAll : 0
        };
    }



    refreshTable() {
        fetch(API).then(response => {
                if (response.ok) {
                    return response;
                }
            throw Error(response.status);
            })
            .then(response => response.json())
            .then(records => {
                let currentTableIndex = this.state.currentIndex;
                records.map((record, index) => {
                    record.tableIndex = currentTableIndex;
                    currentTableIndex = currentTableIndex + 1;

                });
                this.setState({
                    records: records,
                    selected: {},
                    editedContent: {},
                    toDelete: [],
                    currentIndex : currentTableIndex
                });
            })
        .catch(
            error => console.log("Following error occurred: " + error)
        );
    }



    getSelectedRows = () => {
        return Object.keys(
            Object.fromEntries(
                Object.entries(this.state.selected).filter(([k,v]) => v === true)
            )
        ).map(Number);
    }




    editRecord = (original) => {
        const tableIndexOfEdited = JSON.parse(JSON.stringify(original.tableIndex));
        this.setState( (previousState) => ( {
            editedContent : {...previousState.editedContent,
                [tableIndexOfEdited] : {
                    id: original.id,
                    name: original.name,
                    type: original.type,
                    content: original.content,
                    ttl: original.ttl,
                }
            }
        }));
    }


    addRecord = (event) => {
        let currentTableIndex = JSON.parse(JSON.stringify(this.state.currentIndex));
        this.setState((previousState) => ( {
            records : [... previousState.records,
                {
                    id : '',
                    name: '',
                    type: '',
                    content: '',
                    ttl: '',
                    tableIndex : currentTableIndex
                },
            ],
            editedContent : { ...previousState.editedContent,
                [currentTableIndex] : {
                   id : '',
                   name: '',
                   type: '',
                   content: '',
                   ttl: '',
               }
            },
            currentIndex : currentTableIndex + 1
        }));
    }



    deleteRecord = (original) => {
        this.undoEditRecord(original);
        if (original.id != "") {
            this.setState( (previousState) => ( {
                toDelete : [... previousState.toDelete,
                    original.tableIndex]
            }));
        }
        else {
            var records = [...this.state.records];
            var index = records.findIndex(obj => obj.id === original.id);
            records.splice(index, 1);
            this.setState ({
                records : [...records]
            });
        }
    }



    undoDeleteRecord = (original) => {
        let toDelete = [...this.state.toDelete];
        var index = toDelete.indexOf(original.tableIndex);
        toDelete.splice(index, 1);
        this.setState( {
            toDelete : toDelete
        } );
    }



    deleteMultipleRecords = () => {
        const selectedRows = this.getSelectedRows();
        let editedRows = Object.keys(this.state.editedContent).map(Number);

        let committed = []
        let notCommitted = []
        selectedRows.forEach ( (key) => {
            console.log("Selected: " + key);
            var record = this.state.records.find((obj) => {return obj.tableIndex == key});
            if (record.id != "") {
                committed = [...committed, record.tableIndex];
            }
            else {
                notCommitted = [...notCommitted, record.tableIndex]
            }
        });

        console.log("Committed");
        for (var index in committed) {
            console.log(index);
        }


        var editedAndNotSelected = editedRows.filter(function(obj) {
            return !selectedRows.some(function(obj2) {
                return obj == obj2;
            });
        });

        let newEditedContent = {}
        editedAndNotSelected.forEach( (item) => {
            let record = this.state.editedContent[item];
            newEditedContent[item] = {
                id : record.id,
                name: record.name,
                type: record.type,
                content: record.content,
                ttl: record.ttl,
            }
        });

        let newRecords = []
        this.state.records.forEach( record => {
            if (notCommitted.find( (obj) => { return obj === record.tableIndex; }) === undefined) {
                newRecords = [...newRecords, record];
            }
        });

        this.setState( (previousState) => ( {
            editedContent : newEditedContent,
            toDelete : Array.from(new Set([... previousState.toDelete, ...committed])),
            selected: {},
            selectAll: 0,
            records: newRecords,
        }));
    }



    undoEditRecord = (original) => {
        let editedContent = JSON.parse(JSON.stringify(this.state.editedContent));
        delete editedContent[original.tableIndex];
        this.setState( {
            editedContent : editedContent
        } );
    }



    editMultipleRecords = () => {
        let selectedRows = this.getSelectedRows();
        let editedRows = Object.keys(this.state.editedContent).map(Number);

        var selectedAndNotEditedRows = selectedRows.filter(function(obj) {
            return !editedRows.some(function(obj2) {
                return obj == obj2;
            });
        });

        var deletedAndNotSelected = this.state.toDelete.filter(function(obj) {
            return !selectedRows.some(function(obj2) {
                return obj == obj2;
            });
        });

        let newEditedContent = {}
        selectedAndNotEditedRows.forEach( (item) => {
            let record = JSON.parse(JSON.stringify(this.state.records.find(el => el.tableIndex === item)));
            newEditedContent[item] = {
                id : record.id,
                name: record.name,
                type: record.type,
                content: record.content,
                ttl: record.ttl,
            }
        });


        this.setState( (previousState) => ( {
            editedContent: {...previousState.editedContent, ...newEditedContent},
            selected: {},
            selectAll: 0,
            toDelete: deletedAndNotSelected
        }));
    }



    componentDidMount() {
        this.refreshTable();
    }



    setValueInAllEdited = (event, cellInfo) => {
        var selected = this.getSelectedRows();
        var edited = this.state.editedContent;
        Object.values(edited).forEach( (record) => {
            if (selected.find( (obj) => { return obj.tableIndex === record.tableIndex; }) !== undefined) {
                record[cellInfo.column.id] = event.target.value;
            }
        });

        this.setState( (previousState) => ( {
            editedContent: edited
        }));

    }



    changeEditedContent = (event, cellInfo) => {
        let editedRow = this.state.editedContent[cellInfo.original.tableIndex];
        editedRow[cellInfo.column.id] = event.target.value;

        console.log(this.state.editedContent);

        let notEditedRows = {}
        Object.keys(this.state.editedContent)
            .forEach((key) => {
                if (key != cellInfo.original.tableIndex) {
                    notEditedRows[key] = this.state.editedContent[key];
                }
            });

        this.setState({
            editedContent : {...notEditedRows, [cellInfo.original.tableIndex] : editedRow}
        });

    }


    renderNotEditable = cellInfo => {
        return (
            <div
                style={{ backgroundColor: "#fafafa" }}
                dangerouslySetInnerHTML={{
                    __html: this.state.records[cellInfo.index][cellInfo.column.id]
                }}
            />
        );
    };



    renderWhileEdited(cellInfo, inputType) {
        return (
            <div>
                <div
                    style={{ backgroundColor: "#fafafa" }}
                    dangerouslySetInnerHTML={{
                        __html: this.state.records[cellInfo.index][cellInfo.column.id]
                    }}
                />
                <div>
                    <input
                        value={this.state.editedContent[cellInfo.original.tableIndex][cellInfo.column.id]}
                        type = {inputType}
                        style={
                            { backgroundColor: "#fafafa",
                                margin: "3px",
                                width: "98%" }
                        }
                        onChange = { (e) => {this.changeEditedContent(e, cellInfo)}}
                    />
                 </div>
            </div>

        );
    }


    checkIfObjectIsEdited(cellInfo) {
        return (Object.keys(this.state.editedContent)).map(Number)
            .some(item => cellInfo.original.tableIndex === item);
    }



    checkIfObjectIsDeleted(cellInfo) {
        return this.state.toDelete.some(item => cellInfo.original.tableIndex === item);
    }



    setRowProps = (state, rowInfo, column, instance) => {
        if (rowInfo) {
            let isDeleted = this.state.toDelete.some(item => rowInfo.original.tableIndex === item);
            return {
                style: {
                    opacity: isDeleted ? 0.4 : 1.0,
                },
            };
        }
        else {
            return {};
        }
    }



    renderCell(cellInfo, inputType) {
        const isEdited = this.checkIfObjectIsEdited(cellInfo);
        if (isEdited === false) {
            return this.renderNotEditable(cellInfo);
        }
        else {
            return this.renderWhileEdited(cellInfo, inputType);
        }
    }



    renderTextCell = cellInfo => {
        return this.renderCell(cellInfo, 'text');
    }



    renderNumberCell = cellInfo => {
        return this.renderCell(cellInfo, 'number');
    }



    renderFooter = (cellInfo, inputType) => {
        if (this.state.editedContent !== {}) {
            return (<input type={inputType} onChange= { (e) => this.setValueInAllEdited(e, cellInfo)} />)
        }
        else {
            return (<div/>)
        }
    }



    renderNumberFooter = cellInfo => {
        return this.renderFooter(cellInfo, 'number');
    }



    renderTextFooter = cellInfo => {
        return this.renderFooter(cellInfo, 'text');
    }



    renderDeleteButton = cellInfo => {
        let isDeleted = this.checkIfObjectIsDeleted(cellInfo);
        if (isDeleted === false) {
            return (
                <button onClick = { () => this.deleteRecord(cellInfo.original) } > Delete </button>
            );
        }
        else {
            return (
                <button onClick = { () => this.undoDeleteRecord(cellInfo.original) } > Undo delete </button>
            );
        }
    }



    renderEditButton = cellInfo => {
        const isEdited = this.checkIfObjectIsEdited(cellInfo);
        const isDeleted = this.checkIfObjectIsDeleted(cellInfo);
        if (isDeleted) {
            return <div/>
        }
        else if (isEdited) {
            return (
                <button
                    onClick = { () => this.undoEditRecord(cellInfo.original) }
                > Revert changes </button>
            );
        }
        else {
            return (
                <button
                    onClick = { () => this.editRecord(cellInfo.original) }
                > Edit </button>
            );
        }

    }





    toggleRow(recordTableIndex) {
        console.log(this.state.selected);
        const newSelected = JSON.parse(JSON.stringify(this.state.selected));
        newSelected[recordTableIndex] = !this.state.selected[recordTableIndex];
        this.setState({
            selected: newSelected,
            selectAll: 2
        });
    }


    toggleSelectAll() {
        let newSelected = {};

        if (this.state.selectAll === 0) {
            this.state.records.forEach(x => {
                newSelected[x.tableIndex] = true;
            });
        }

        this.setState({
            selected: newSelected,
            selectAll: this.state.selectAll === 0 ? 1 : 0
        });
    }



    revertChanges() {
        this.refreshTable();
    }


    commitChanges = () => {
        let editedContent = Object.values(this.state.editedContent);
        fetch('http://localhost:8001/records/commit', {
            method: 'post',
            body: JSON.stringify(editedContent),
            headers: {'Content-Type': 'application/json'}
        }).then((response) => {
            return response;
        }).then((data) => {
            console.log('Complete', data);
            this.deleteRecordsFromDb();
        });
    }

    deleteRecordsFromDb = () => {
        let deletedItems = [];
        this.state.toDelete.forEach( (item) => {
            let recordToDelete = this.state.records.find( (record) => {
                return record.tableIndex === item;
            });
            deletedItems = [...deletedItems, recordToDelete];
        });
        fetch('http://localhost:8001/records/delete', {
            method: 'post',
            body: JSON.stringify(deletedItems),
            headers: {'Content-Type': 'application/json'}
        }).then((response) => {
            return response;
        }).then((data) => {
            console.log('Complete', data);
            this.setState({toDelete : []});
            this.refreshTable();
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
                        checked={this.state.selected[JSON.stringify(original.tableIndex)] === true}
                        onChange = { () => this.toggleRow(original.tableIndex) }
                    />
                );
            },
            sortable: false,
            filterable: false,
            width: 45,
        },
        {
            Header : "ID",
            accessor: 'id',
            sortable: false
        },
        {
            Header : 'Name',
            accessor: 'name',
            sortable: false,
            Cell: this.renderTextCell,
            Footer : this.renderTextFooter
        },
        {
            Header : 'Type',
            accessor: 'type',
            sortable: false,
            Cell: this.renderTextCell,
            Footer : this.renderTextFooter
        },
        {
            Header : 'Content',
            accessor: 'content',
            sortable: false,
            Cell: this.renderTextCell,
            Footer : this.renderTextFooter
        },
        {
            Header : 'TTL',
            accessor: 'ttl',
            sortable: false,
            Cell: this.renderNumberCell,
            Footer : this.renderNumberFooter
        },
        {
            Header : 'Delete',
            accessor: '',
            Cell: this.renderDeleteButton,
            sortable: false,
            filterable: false,
            Footer : (
                <button onClick= { () => this.deleteMultipleRecords() } > Delete selected </button>
            )
        },
        {
            Header : 'Edit',
            accessor: '',
            Cell: this.renderEditButton,
            sortable: false,
            filterable: false,
            Footer : (
                <button onClick= { () => this.editMultipleRecords() } > Edit selected </button>
            )

        }
        ];

        return (
            <div>
                <div className="buttonsWrapper">
                    <button onClick={() => this.addRecord()}> Add record</button>
                    <button onClick={() => this.commitChanges()}> Commit changes</button>
                    <button onClick={() => this.revertChanges()}> Revert changes</button>
                </div>
                <ReactTable
                    data={this.state.records}
                    filterable
                    defaultSorted={[{
                      id   : 'tableIndex',
                      desc : false,
                    }]}
                    columns={columns}
                    getTrProps = {this.setRowProps}
                />
            </div>
        );
    }



    render() {
        return this.renderTable();
    }

}


export default Records;