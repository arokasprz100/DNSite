import React from "react";
import useRef from "react";
import "./resources/styles/Supermasters.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import ReactTable from "react-table";
import "react-table/react-table.css";
import {Link} from "react-router-dom";


class ReusableTable extends React.Component
{

    constructor(props)
    {
        super(props);

        this.tableModes = {
            EDIT: 'edit',
            COMMITTED: 'committed'
        };

        this.state = {
            data : [],
            selected : {},
            toDelete : [],
            editedContent : {},

            currentIndex : 0,
            selectAll : 0,

            valueConstraints : {},

            expanded:{},
            errorMessages: [],

            tableMode : this.tableModes.EDIT

        };

        this.emptyDataExample = this.props.emptyDataExample;
        this.fetchValueConstraints = this.props.fetchValueConstraints.bind(this);
        this.renderTable = this.renderTableInEditMode;

        this.table = {};
    }


    refreshTable = () =>
    {
        this.fetchValueConstraints();

        fetch(this.props.resourcesURLBase + this.props.resourcesSelectURL)
        .then(response =>
        {
            if (response.ok) { return response; }
            throw Error(response.status);
        })
        .then(response => response.json())
        .then(data =>
        {
            let currentTableIndex = this.state.currentIndex;
            data.map( (row, index) =>
            {
                row.tableIndex = currentTableIndex;
                row.isNewlyAdded = false;
                currentTableIndex = currentTableIndex + 1;
            });
            this.setState
            ({
                data: data,
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

    getSelectedRows = () =>
    {
        return Object.keys(
            Object.fromEntries(
                Object.entries(this.state.selected).filter(([k,v]) => v === true)
            )
        ).map(Number);
    }

    editRow = (original) =>
    {
        let originalCopy = JSON.parse(JSON.stringify(original));
        const tableIndexOfEdited = JSON.parse(JSON.stringify(original.tableIndex));
        this.setState( (previousState) => ( {
            editedContent : {...previousState.editedContent,
                [tableIndexOfEdited] : originalCopy
            }
        }));
    }

    addRow = (event) =>
    {
        let newRow = JSON.parse(JSON.stringify(this.emptyDataExample));
        let newEditedRow = JSON.parse(JSON.stringify(this.emptyDataExample));
        let currentTableIndex = JSON.parse(JSON.stringify(this.state.currentIndex));
        newRow.tableIndex = currentTableIndex;
        newRow.isNewlyAdded = true;
        newEditedRow.tableIndex = currentTableIndex;
        newEditedRow.isNewlyAdded = true;

        this.setState((previousState) =>
        ({
            data : [ ... previousState.data, newRow ],
            editedContent : {  ...previousState.editedContent, [currentTableIndex] : newEditedRow },
            currentIndex : currentTableIndex + 1
        }));
    }



    deleteRow = (original) =>
    {
        this.undoEditRow(original);
        if (original.id !== '')
        {
            this.setState( (previousState) => ( {
                toDelete : [... previousState.toDelete,
                    original.tableIndex]
            }));
        }
        else {
            var editedData = [...this.state.data];
            var index = editedData.findIndex(obj => obj.tableIndex === original.tableIndex);
            editedData.splice(index, 1);
            this.setState ({
                data : [...editedData]
            });
        }
    }


    undoDeleteRow = (original) =>
    {
        let toDelete = [...this.state.toDelete];
        var index = toDelete.indexOf(original.tableIndex);
        toDelete.splice(index, 1);
        this.setState( {
            toDelete : toDelete
        } );
    }


    deleteMultipleRows = () =>
    {
        const selectedRows = this.getSelectedRows();
        let editedRows = Object.keys(this.state.editedContent).map(Number);

        let committed = [];
        let notCommitted = [];
        selectedRows.forEach ( (key) => {
            var row = this.state.data.find((obj) => {return obj.tableIndex == key});
            if (row.id !== '') {
                committed = [...committed, row.tableIndex];
            }
            else {
                notCommitted = [...notCommitted, row.tableIndex]
            }
        });

        var editedAndNotSelected = editedRows.filter(function(obj) {
            return !selectedRows.some(function(obj2) {
                return obj == obj2;
            });
        });

        let newEditedContent = {}
        editedAndNotSelected.forEach( (item) => {
            let rowCopy = JSON.parse(JSON.stringify(this.state.editedContent[item]));
            newEditedContent[item] = rowCopy
        });

        let newData = []
        this.state.data.forEach ( row =>
        {
            if (notCommitted.find( (obj) => { return obj === row.tableIndex; }) === undefined)
            {
                newData = [...newData, row];
            }
        });

        this.setState( (previousState) => ( {
            editedContent : newEditedContent,
            toDelete : Array.from(new Set([... previousState.toDelete, ...committed])),
            selected: {},
            selectAll: 0,
            data: newData,
        }));
    }



    undoEditRow = (original) =>
    {
        let editedContent = JSON.parse(JSON.stringify(this.state.editedContent));
        delete editedContent[original.tableIndex];
        this.setState( {
            editedContent : editedContent
        } );
    }


    // TODO: check
    editMultipleRows = () =>
    {
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
            let rowCopy = JSON.parse(JSON.stringify(this.state.data.find(el => el.tableIndex === item)));
            newEditedContent[item] = rowCopy
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



    setValueInAllEdited = (event, cellInfo) =>
    {
        var selected = this.getSelectedRows();
        console.log(selected);
        var edited = JSON.parse(JSON.stringify(this.state.editedContent));
        Object.values(edited).forEach( (editedRow) =>
        {
            if (selected.find( (selectedTableIndex) => { return selectedTableIndex === editedRow.tableIndex; }) !== undefined) {
                editedRow[cellInfo.column.id] = event.target.value;
            }
        });
        console.log(edited);
        this.setState( (previousState) => ( {
            editedContent: edited
        }));
    }

    changeEditedContent = (event, cellInfo) =>
    {
        let editedRow = JSON.parse(JSON.stringify(this.state.editedContent[cellInfo.original.tableIndex]));
        editedRow[cellInfo.column.id] = event.target.value;

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


    renderNotEditable = cellInfo =>
    {
        return (
            <div
                style={{ backgroundColor: "#fafafa" }}
                dangerouslySetInnerHTML={{
                    __html: this.state.data[cellInfo.index][cellInfo.column.id]
                }}
            />
        );
    };



    renderWhileEdited = (cellInfo, inputType) =>
    {
        return (
            <div>
                <div
                    style={{ backgroundColor: "#fafafa" }}
                    dangerouslySetInnerHTML={{
                        __html: this.state.data[cellInfo.index][cellInfo.column.id]
                    }}
                />
                <div>
                    <input
                        value = { this.state.editedContent[cellInfo.original.tableIndex][cellInfo.column.id] }
                        type = {inputType}
                        style = { { backgroundColor: "#fafafa", margin: "3px", width: "98%" } }
                        onChange = { (e) => {this.changeEditedContent(e, cellInfo)}}
                    />
                 </div>
            </div>

        );
    }


    checkIfObjectIsEdited = (cellInfo) =>
    {
        return (Object.keys(this.state.editedContent)).map(Number)
            .some(item => cellInfo.original.tableIndex === item);
    }



    checkIfObjectIsDeleted = (cellInfo) =>
    {
        return this.state.toDelete.some(item => cellInfo.original.tableIndex === item);
    }



    setRowProps = (state, rowInfo, column, instance) =>
    {

        let properties = {
            onClick: (e) => {
                console.log("xddddd");
            }
        };

        if (rowInfo) {
            let isDeleted = this.state.toDelete.some(item => rowInfo.original.tableIndex === item);
            let isIncorrect = (rowInfo.original.tableIndex in this.state.expanded
                && this.state.expanded[rowInfo.original.tableIndex] === true);
            properties.style = {
                opacity: isDeleted ? 0.4 : 1.0,
                backgroundColor : isIncorrect ? "red" : "white",
            };
            return properties;
        }
        else {
            return properties;
        }
    }



    renderCell = (cellInfo, inputType) =>
    {
        const isEdited = this.checkIfObjectIsEdited(cellInfo);
        if (isEdited === false) {
            return this.renderNotEditable(cellInfo);
        }
        else {
            return this.renderWhileEdited(cellInfo, inputType);
        }
    }



    renderTextCell = cellInfo =>
    {
        return this.renderCell(cellInfo, 'text');
    }



    renderNumberCell = cellInfo =>
    {
        return this.renderCell(cellInfo, 'number');
    }


    renderSelectCell = cellInfo =>
    {
        const isEdited = this.checkIfObjectIsEdited(cellInfo);
        if (isEdited === false) {
            return this.renderNotEditable(cellInfo);
        }

        let constraintName = cellInfo.column.id + "s";
        return (
            <div>
                <div
                    style = { { backgroundColor: "#fafafa" } }
                    dangerouslySetInnerHTML = { {__html: this.state.data[cellInfo.index][cellInfo.column.id] } }
                />
                <div>
                    <select
                        value = { this.state.editedContent[cellInfo.original.tableIndex][cellInfo.column.id] }
                        style = { { backgroundColor: "#fafafa", margin: "3px", width: "98%" } }
                        onChange = { (e) => {this.changeEditedContent(e, cellInfo)}}
                    >
                     <option key='' value=''>--</option>;
                    {
                        this.state.valueConstraints[constraintName].map((key) => {
                            return <option key={key} value={key}>{key}</option>;
                        })
                    }
                    </select>
                 </div>
            </div>

        );
    }



    renderFooter = (cellInfo, inputType) =>
    {
        if (Object.getOwnPropertyNames(this.state.editedContent).length != 0) {
            return (<input type={inputType} onChange= { (e) => this.setValueInAllEdited(e, cellInfo)} />)
        }
        else {
            return (<div/>)
        }
    }



    renderNumberFooter = cellInfo =>
    {
        return this.renderFooter(cellInfo, 'number');
    }



    renderTextFooter = cellInfo =>
    {
        return this.renderFooter(cellInfo, 'text');
    }


    renderSelectFooter = cellInfo =>
    {
        let constraintName = cellInfo.column.id + "s";
        if (Object.getOwnPropertyNames(this.state.editedContent).length != 0)
        {
            return (<select onChange= { (e) => this.setValueInAllEdited(e, cellInfo)} >
                {
                    this.state.valueConstraints[constraintName].map((key) => {
                        return <option key={key} value={key}>{key}</option>;
                    })
                }
            </select>)
        }
        else {
            return (<div/>)
        }
    }



    renderDeleteButton = cellInfo =>
    {
        let isDeleted = this.checkIfObjectIsDeleted(cellInfo);
        if (isDeleted === false) {
            return (
                <button onClick = { () => this.deleteRow(cellInfo.original) } > Delete </button>
            );
        }
        else {
            return (
                <button onClick = { () => this.undoDeleteRow(cellInfo.original) } > Undo delete </button>
            );
        }
    }



    renderEditButton = cellInfo =>
    {
        const isEdited = this.checkIfObjectIsEdited(cellInfo);
        const isDeleted = this.checkIfObjectIsDeleted(cellInfo);
        if (isDeleted) { return <div/> }
        else if (isEdited)
        {
            return ( <button onClick = { () => this.undoEditRow(cellInfo.original) } > Revert changes </button> );
        }
        else
        {
            return ( <button onClick = { () => this.editRow(cellInfo.original) } > Edit </button> );
        }

    }


    renderCommittedCell = (cellInfo) =>
    {
        const hasBeenEdited = this.checkIfObjectIsEdited(cellInfo);
        if (hasBeenEdited) {

            return (
                <div style={{ backgroundColor: "#7CFC00" }}>
                    <div
                        style={{ backgroundColor: "#fafafa" }}
                        dangerouslySetInnerHTML = {{ __html: this.state.data[cellInfo.index][cellInfo.column.id] }}
                    />
                    <div
                        dangerouslySetInnerHTML = {{ __html: this.state.editedContent[cellInfo.original.tableIndex][cellInfo.column.id] }}
                    />
                </div>
            );
        }
        else {
            return (
                <div
                    style={{ backgroundColor: "#fafafa" }}
                    dangerouslySetInnerHTML={{
                        __html: this.state.data[cellInfo.index][cellInfo.column.id]
                    }}
                />
            );
        }
    }


    renderCellEditableOnlyWhenRowIsNew = (cellInfo) =>
    {
        if ( this.state.data[cellInfo.index].isNewlyAdded === true) {
            console.log("Here");
            return this.renderCell(cellInfo, "text");
        }
        else {
            console.log("Here2");
            return this.renderNotEditable(cellInfo);
        }
    }


    checkRowsSelectionStatus = (newSelected, visibleRows) =>
    {
        let selectedCount = Object.keys(
            Object.fromEntries(Object.entries(newSelected).filter(([k,v]) => v === true))).map(Number).length;
        let visibleRowsCount = visibleRows.length;

        console.log("Selected count ", selectedCount);
        console.log("Data count ", visibleRowsCount);

        if (selectedCount === 0) return 0;
        else if (selectedCount === visibleRowsCount) return 1;
        else return 2;
    }


    toggleRow = (rowTableIndex) =>
    {
        const newSelected = JSON.parse(JSON.stringify(this.state.selected));
        newSelected[rowTableIndex] = !this.state.selected[rowTableIndex];

        const visibleRows = this.table.getResolvedState().sortedData;
        let selectAllValue = this.checkRowsSelectionStatus(newSelected, visibleRows);

        console.log("SelectAll ", selectAllValue);

        this.setState({
            selected: newSelected,
            selectAll: selectAllValue
        });
    }

    // TODO: fix
    toggleSelectAll = () =>
    {
        let newSelected = JSON.parse(JSON.stringify(this.state.selected));
        let visibleRows = this.table.getResolvedState().sortedData;
        if (this.state.selectAll === 0 || this.state.selectAll === 1) {
            visibleRows.forEach( row => { newSelected[row[""].tableIndex] = true; } );
        }
        else {
            visibleRows.forEach( row => { newSelected[row[""].tableIndex] = false; } );
        }

        let selectAllValue = this.checkRowsSelectionStatus(newSelected, visibleRows);

        this.setState({
            selected: newSelected,
            selectAll: selectAllValue
        });
    }



    revertChanges = () =>
    {
        this.refreshTable();
    }


    commitChanges = () =>
    {
        let editedContent = Object.values(this.state.editedContent);
        let deletedContent = [];
        this.state.toDelete.forEach( (item) => {
            let rowToDelete = this.state.data.find( (row) => {
                return row.tableIndex === item;
            });
            deletedContent = [...deletedContent, rowToDelete];
        });

        console.log(editedContent);

        Promise.all
        ([
            fetch(this.props.resourcesURLBase + 'commit', { method: 'post', body: JSON.stringify(editedContent),
                headers: {'Content-Type': 'application/json' } } ),
            fetch(this.props.resourcesURLBase + 'delete', { method: 'post', body: JSON.stringify(deletedContent),
                headers: {'Content-Type': 'application/json' } } )
        ])
        .then( ([response1, response2]) => response1.json())
        .then( (response1) =>
        {

            if (response1.length === 0) {
                this.renderTable = this.renderTableInReadOnlyMode;
                this.setState({
                    tableMode: this.tableModes.COMMITTED
                });
            }
            else {
                let expandedRows = JSON.parse(JSON.stringify(this.state.expanded));
                response1.map ( (errorMessage, index) => {
                    expandedRows[errorMessage.rowNumber] = true;
                } );


                this.setState({
                    expanded : expandedRows,
                    errorMessages : response1
                });

            }
        })
    }


    continueAfterChanges = () =>
    {
        this.renderTable = this.renderTableInEditMode;
        this.setState( { toDelete : [], tableMode: this.tableModes.EDIT } );
        this.refreshTable();
    }


    renderTableInReadOnlyMode()
    {
        let dataColumns = [];
        this.props.columns.forEach ( (columnMetaData) => {

            let dataColumn = {
                Header : columnMetaData.Header,
                accessor : columnMetaData.accessor,
                sortable : false,
                resizable : false,
                Cell: this.renderCommittedCell
            };

            dataColumns = [...dataColumns, dataColumn];

        });

        const columns = [...dataColumns, { expander: true, show: false }];
        return (
            <div>
                <div className="buttonsWrapper">
                    <button onClick={ () => this.continueAfterChanges() }> Continue </button>
                </div>
                <ReactTable
                    ref={(r) => (this.table = r)}
                    data={this.state.data}
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


    renderTableInEditMode()
    {
        let dataColumns = [];
        this.props.columns.forEach ( (columnMetaData) => {

            let dataColumn = {
                Header : columnMetaData.Header,
                accessor : columnMetaData.accessor,
                sortable : false,
                resizable : false
            };

            if (columnMetaData.type === "select")
            {
                dataColumn.Cell = this.renderSelectCell;
                dataColumn.Footer = this.renderSelectFooter;
            }
            else if (columnMetaData.type === "number")
            {
                dataColumn.Cell = this.renderNumberCell;
                dataColumn.Footer = this.renderNumberFooter;
            }
            else if (columnMetaData.type === "text")
            {
                dataColumn.Cell = this.renderTextCell;
                dataColumn.Footer = this.renderTextFooter;
            }
            else if (columnMetaData.type === "link")
            {
                dataColumn.Cell = ({original}) =>
                {
                    return (
                        <Link to = { this.props.resourceName + "/" + original[columnMetaData.accessor]}>
                            {original[columnMetaData.accessor]}
                        </Link>
                    );
                }
            }
            else if (columnMetaData.type === "text_editableOnlyOnAdd") {
                // TODO: add footer
                dataColumn.Cell = this.renderCellEditableOnlyWhenRowIsNew;
            }

            dataColumns = [...dataColumns, dataColumn];

        });

        const columns = [
            {
                Header : x => {
                    return (
                        <input
                            type = "checkbox"
                            checked = { this.state.selectAll === 1 }
                            ref = { input => { if (input) { input.indeterminate = this.state.selectAll === 2; } } }
                            onChange={ () => this.toggleSelectAll() }
                        />
                    );
                },
                id : "checkbox",
                accessor: "",
                Cell: ({original}) => {
                    return (
                        <input
                            type = "checkbox"
                            className = "checkbox"
                            checked = { this.state.selected[JSON.stringify(original.tableIndex)] === true }
                            onChange = { () => this.toggleRow(original.tableIndex) }
                        />
                    );
                },
                sortable: false,
                filterable: false,
                resizable: false,
                width: 45,
            },
            ...dataColumns,
            {
                Header : 'Delete',
                accessor: '',
                Cell: this.renderDeleteButton,
                sortable: false,
                filterable: false,
                resizable: false,
                Footer : ( <button onClick= { () => this.deleteMultipleRows() } > Delete selected </button> )
            },
            {
                Header : 'Edit',
                accessor: '',
                Cell: this.renderEditButton,
                sortable: false,
                filterable: false,
                resizable: false,
                Footer : ( <button onClick= { () => this.editMultipleRows() } > Edit selected </button> )
            },
            {
              expander: true,
              show: false
            }
        ];
        return (
            <div>
                <div className="buttonsWrapper">
                    <button onClick={ () => this.addRow() }> Add {this.props.resourceName} </button>
                    <button onClick={ () => this.commitChanges() }> Commit changes </button>
                    <button onClick={ () => this.revertChanges() }> Revert changes </button>
                </div>
                <ReactTable
                    ref={(r) => (this.table = r)}
                    data={this.state.data}
                    filterable
                    defaultSorted={[{
                      id   : 'tableIndex',
                      desc : false,
                    }]}
                    columns = {columns}
                    getTrProps = {this.setRowProps}
                    expanded = {this.state.expanded}
                    SubComponent = { row => {
                        return (
                            <div style={{ padding: '10px', backgroundColor : 'red', }} >
                                <ul>
                                    {
                                        this.state.errorMessages.filter( (errorMessage) => {
                                            return errorMessage.rowNumber === row.original.tableIndex;
                                        }).
                                        map ( (errorMessage, index) => {
                                            return <li> Error in {errorMessage.field}  :  {errorMessage.message} </li>
                                        })
                                    }
                                </ul>
                            </div>
                        )
                    }}
                />
            </div>
        );
    }

    renderTable = () => {}

    render() {
        return this.renderTable();
    }

}

export default ReusableTable;