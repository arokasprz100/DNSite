import React from "react";
import "../styles/Supermasters.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import ReactTable from "react-table";
import "react-table/react-table.css";
import {Link} from "react-router-dom";


class ReusableTable extends React.Component
{

    constructor(props)
    {
        super(props);

        this.state = {

            // data stored in table
            data : [],
            valueConstraints : {},
            toDelete : [],
            editedContent : {},
            currentIndex : 0,

            // selection (checkbox) mechanism
            selected : {},
            selectAll : 0,

            // validation error display mechanism
            expanded: {},
            errorMessages: [],

            // enable copying mechanism
            isSmartCopyEnabled: false,

            // copying row mechanism
            focusedRow: null,
            copiedRow : {},

            // copying single value mechanism
            focusedCell: { rowNumber: null, columnName: null},
            copiedCell : {columnName: null, value : null}
        };

        this.emptyDataExample = this.props.emptyDataExample;
        this.fetchValueConstraints = this.props.fetchValueConstraints.bind(this);
        this.columnsSchema = this.props.columns;
        this.renderTable = this.renderTableInEditMode;

        this.table = {};
        this.tableDiv = {};
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
            let currentTableIndex = 0;
            data.forEach( (row) =>
            {
                row.tableIndex = currentTableIndex;
                row.isNewlyAdded = false;
                currentTableIndex = currentTableIndex + 1;
            });
            let expanded = this.getAllRowsExpanded();
            this.setState ({
                data: data,
                selected: {},
                editedContent: {},
                toDelete: [],
                currentIndex : currentTableIndex,
                errorMessages: [],
                expanded: expanded
            });
        })
        .catch(
            error => console.log("Following error occurred: " + error)
        );
    }


    toggleSmartCopy = () =>
    {
        const newValue = !this.state.isSmartCopyEnabled;
        this.setState ({ isSmartCopyEnabled: newValue });
    }


    renderSmartCopyButton = () =>
    {
        const buttonText = (this.state.isSmartCopyEnabled ? "Disable smart copy" : "Enable smart copy");
        return (<button onClick = { () => { this.toggleSmartCopy() } }> {buttonText} </button>);
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
            data : [ ...previousState.data, newRow ],
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
                toDelete : [...previousState.toDelete,
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
            var row = this.state.data.find((obj) => {return obj.tableIndex === key});
            if (row.id !== '') {
                committed = [...committed, row.tableIndex];
            }
            else {
                notCommitted = [...notCommitted, row.tableIndex]
            }
        });

        var editedAndNotSelected = editedRows.filter(function(obj) {
            return !selectedRows.some(function(obj2) {
                return obj === obj2;
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
            toDelete : Array.from(new Set([...previousState.toDelete, ...committed])),
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


    editMultipleRows = () =>
    {
        let selectedRows = this.getSelectedRows();
        let editedRows = Object.keys(this.state.editedContent).map(Number);

        var selectedAndNotEditedRows = selectedRows.filter(function(obj) {
            return !editedRows.some(function(obj2) {
                return obj === obj2;
            });
        });

        var deletedAndNotSelected = this.state.toDelete.filter(function(obj) {
            return !selectedRows.some(function(obj2) {
                return obj === obj2;
            });
        });

        let newEditedContent = {}
        selectedAndNotEditedRows.forEach( (item) => {
            let rowCopy = JSON.parse(JSON.stringify(this.state.data.find(el => el.tableIndex === item)));
            newEditedContent[item] = rowCopy
        });


        this.setState( (previousState) => ( {
            editedContent: {...previousState.editedContent, ...newEditedContent},
            toDelete: deletedAndNotSelected
        }));
    }



    componentDidMount() {
        document.addEventListener("keydown", this.handleKeyDown);
        document.addEventListener("click", this.handleClick);
        this.refreshTable();
    }


    componentWillUnmount() {
        document.removeEventListener("click", this.handleClick);
        document.removeEventListener("keydown", this.handleKeyDown);
    }


    handleClick = (event) => {
        if (!this.tableDiv.contains(event.target) && (this.state.focusedRow !== null || this.state.focusedCell.columnName !== null)) {
            this.setState({
                focusedRow: null,
                focusedCell: {columnName: null, rowNumber: null}
            });
        }
    }



    setValueInAllEdited = (event, cellInfo) =>
    {
        var selected = this.getSelectedRows();
        var edited = JSON.parse(JSON.stringify(this.state.editedContent));
        Object.values(edited).forEach( (editedRow) =>
        {
            if (selected.find( (selectedTableIndex) => { return selectedTableIndex === editedRow.tableIndex; }) !== undefined) {
                editedRow[cellInfo.column.id] = event.target.value;
            }
        });
        this.setState( (previousState) => ( {
            editedContent: edited
        }));
    }

    changeEditedContent = (event, cellInfo) =>
    {
        let editedRow = JSON.parse(JSON.stringify(this.state.editedContent[cellInfo.original.tableIndex]));
        editedRow[cellInfo.column.id] = event.target.value === '' ? null : event.target.value; // TODO: work on backend to validate empty strings
        let notEditedRows = {}
        Object.keys(this.state.editedContent)
            .forEach((key) => {
                if (key !== cellInfo.original.tableIndex) {
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
        let secondRow = ( inputType !== "textarea" ?
            <input
                value = { this.state.editedContent[cellInfo.original.tableIndex][cellInfo.column.id] }
                type = {inputType}
                style = { { backgroundColor: "#fafafa", margin: "3px", width: "98%" } }
                onChange = { (e) => {this.changeEditedContent(e, cellInfo)}}
            />
            :
            <textarea
                value = { this.state.editedContent[cellInfo.original.tableIndex][cellInfo.column.id] }
                onChange = { (e) => {this.changeEditedContent(e, cellInfo)}}
                cols = "45"
                rows = "3">
            </textarea>
        );
        return (
            <div>
                <div
                    style={{ backgroundColor: "#fafafa" }}
                    dangerouslySetInnerHTML={{
                        __html: this.state.data[cellInfo.index][cellInfo.column.id]
                    }}
                />
                <div>
                    { secondRow }
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
        let properties = {};
        if (rowInfo) {
            let isDeleted = this.state.toDelete.some(item => rowInfo.original.tableIndex === item);
            let isIncorrect = this.state.errorMessages.filter ( (errorMessage) => {
                return errorMessage.rowNumber === rowInfo.original.tableIndex;
            }).length;
            let isFocused = (this.state.focusedRow === rowInfo.original.tableIndex);
            properties.style = {
                opacity: isDeleted ? 0.4 : 1.0,
                backgroundColor: isFocused ? "#f0f0f0" : (isIncorrect !== 0 ? "rgb(255,184,184)" : "transparent"),
                boxShadow: isIncorrect !== 0 ? "0 4px 20px -10px grey" : 'none',
                zIndex:isIncorrect !== 0 ? "1" : "initial",
            };
        }
        return properties;
    }


    setReadonlyRowProps = (state, rowInfo, column, instance) =>
    {
        let properties = {};
        if (rowInfo) {
            let isEdited = (rowInfo.original.tableIndex in this.state.editedContent);
            let isDeleted = this.state.toDelete.some(item => rowInfo.original.tableIndex === item);
            if (isDeleted) {
                properties.style = { opacity: 0.4 }
            }
            else if (isEdited) {
                properties.style = { backgroundColor: "#daffd9" };
            }

        }

        return properties;
    }


    setCellProps = (state, row, col, instance) =>
    {
        let properties = {
            onClick: (e) => {
                if (row && col)
                {
                    if (e.ctrlKey)
                    {
                        if (col.id !== "checkbox" && col.id !== "edit" && col.id !== "delete")
                        {
                            this.setState({
                                focusedRow : null,
                                focusedCell : { columnName : col.id, rowNumber : row.original.tableIndex}
                            });
                        }
                    }
                    else
                    {
                        this.setState({
                            focusedRow : row.original.tableIndex,
                            focusedCell: { columnName : null, rowNumber : null}
                        });
                    }
                }
            }
        };

        if (row && col) {
            let isFocused = (this.state.focusedCell.columnName === col.id && this.state.focusedCell.rowNumber === row.original.tableIndex);
            properties.style = {
                backgroundColor: isFocused ? "#f0f0f0" : "transparent",
            }
        }

        return properties;
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


    renderTextAreaCell = cellInfo =>
    {
        return this.renderCell(cellInfo, 'textarea');
    }


    renderNumberCell = cellInfo =>
    {
        return this.renderCell(cellInfo, 'number');
    }


    renderBoolCell = cellInfo =>
    {
        const isEdited = this.checkIfObjectIsEdited(cellInfo);
        if (isEdited === false) {
            return this.renderNotEditable(cellInfo);
        }
        else {
            return (
                <div>
                    <div
                        style={{ backgroundColor: "#fafafa", width: "100%" }}
                        dangerouslySetInnerHTML={{
                            __html: this.state.data[cellInfo.index][cellInfo.column.id]
                        }}
                    />
                    <div>
                        <select
                            value = { this.state.editedContent[cellInfo.original.tableIndex][cellInfo.column.id] }
                            style = { { backgroundColor: "#fafafa", margin: "3px", width: "98%" } }
                            onChange = { (e) => { this.changeEditedContent(e, cellInfo)}}
                        >
                            <option key="" value=""> -- </option>
                            <option key={true} value={true}> true </option>
                            <option key={false} value={false}> false </option>
                        </select>
                    </div>
                </div>

            );
        }
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
                        onChange = { (e) => { this.changeEditedContent(e, cellInfo)}}
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
        if (Object.getOwnPropertyNames(this.state.editedContent).length !== 0) {
            if (inputType !== "textarea") {
                return (<input type={inputType} onChange= { (e) => this.setValueInAllEdited(e, cellInfo)} />)
            }
            else {
                return (<textarea
                    onChange = { (e) => {this.setValueInAllEdited(e, cellInfo)}}
                    cols = "45"
                    rows = "3">
                </textarea>)
            }
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


    renderTextAreaFooter = cellInfo =>
    {
        return this.renderFooter(cellInfo, 'textarea');
    }

    renderBoolFooter = cellInfo =>
    {
        if (Object.getOwnPropertyNames(this.state.editedContent).length !== 0) {
            return (
                <select
                    style = { { margin: "3px", width: "98%" } }
                    onClick = { (e) => this.setSelectValueInAllEdited(e, cellInfo)}
                >
                    <option key="" value=""> -- </option>
                    <option key={true} value={true}> true </option>
                    <option key={false} value={false}> false </option>
                </select>
            )
        }
        else {
            return (<div/>)
        }

    }


    renderSelectFooter = cellInfo =>
    {
        let constraintName = cellInfo.column.id + "s";
        if (Object.getOwnPropertyNames(this.state.editedContent).length !== 0)
        {
            return (<select onClick = { (e) => this.setSelectValueInAllEdited(e, cellInfo)} >
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


    setSelectValueInAllEdited = (e, cellInfo) =>
    {
        if (e.detail === 0) {
            this.setValueInAllEdited(e, cellInfo);
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
        const isNewlyAdded = cellInfo.original.isNewlyAdded;
        if (isDeleted || isNewlyAdded) { return <div/> }
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
                <div>
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
            return this.renderCell(cellInfo, "text");
        }
        else {
            return this.renderNotEditable(cellInfo);
        }
    }



    toggleRow = (rowTableIndex) =>
    {
        const newSelected = JSON.parse(JSON.stringify(this.state.selected));
        newSelected[rowTableIndex] = !this.state.selected[rowTableIndex];

        this.setState({
            selected: newSelected
        }, this.handleSelectionChange);
    }


    toggleSelectAll = () =>
    {
        let newSelectAll = 0;
        if (this.state.selectAll === 2) newSelectAll = 1;
        else if (this.state.selectAll === 1) newSelectAll = 0;
        else newSelectAll = 1;

        let newSelected = JSON.parse(JSON.stringify(this.state.selected));
        let visibleRows = this.table.getResolvedState().sortedData;
        if (newSelectAll === 1) {
            visibleRows.forEach( row => { newSelected[row._original.tableIndex] = true; } );
        }
        else {
            visibleRows.forEach( row => { newSelected[row._original.tableIndex] = false; } );
        }

        this.setState({
            selected: newSelected
        }, this.handleSelectionChange);
    }


    handleSelectionChange = () =>
    {
        let visibleRows = this.table.getResolvedState().sortedData;
        let filteredSelectedCount = 0;
        visibleRows.forEach( (row) => {
            if (row._original.tableIndex in this.state.selected && this.state.selected[row._original.tableIndex] === true) {
                ++filteredSelectedCount;
            }
        });

        let selectAll = 0;
        if (filteredSelectedCount === 0) {
            selectAll = 0;
        }
        else if (filteredSelectedCount === visibleRows.length) {
            selectAll = 1;
        }
        else {
            selectAll = 2;
        }

        this.setState({ selectAll : selectAll });
    }


    handleCtrlCEvent = (event) =>
    {
        event.preventDefault();
        if (this.state.focusedRow !== null)
        {
            let focusedRowContent = JSON.parse(JSON.stringify(
                this.state.data.find((element) => {return element.tableIndex === this.state.focusedRow})));
            let copiedData = {};

            for (const column of this.columnsSchema) {
                if (column.type === "text" || column.type === "textarea" || column.type === "select" || column.type === "number" || column.type == "text_editableOnlyOnAdd") {
                    copiedData[column.accessor] = focusedRowContent[column.accessor];
                }
            }

            this.setState({ copiedRow : copiedData });

        }
        else if (this.state.focusedCell.columnName !== null && this.state.focusedCell.rowNumber !== null)
        {
            let focusedRow = JSON.parse(JSON.stringify(this.state.data.find((element) => {return element.tableIndex === this.state.focusedCell.rowNumber})));
            let copiedValue = focusedRow[this.state.focusedCell.columnName];

            this.setState( {copiedCell : {columnName : this.state.focusedCell.columnName, value : copiedValue}});
        }
    }


    handleCtrlXEvent = (event) =>
    {
        event.preventDefault();
        if (this.state.focusedRow !== null)
        {
            if (!(this.state.focusedRow in this.state.editedContent)) {
                return;
            }

            let focusedRowContent = JSON.parse(JSON.stringify(this.state.editedContent[this.state.focusedRow]));
            let copiedData = {};

            for (const column of this.columnsSchema) {
                if (column.type === "text" || column.type === "textarea" || column.type === "select" || column.type === "number" || column.type === "bool" || column.type == "text_editableOnlyOnAdd") {
                    copiedData[column.accessor] = focusedRowContent[column.accessor];
                }
            }

            this.setState({ copiedRow : copiedData });
        }
        else if (this.state.focusedCell.columnName !== null && this.state.focusedCell.rowNumber !== null)
        {
            if (!(this.state.focusedCell.rowNumber in this.state.editedContent)) {
                return;
            }

            let focusedRowContent = JSON.parse(JSON.stringify(this.state.editedContent[this.state.focusedCell.rowNumber]));
            let copiedValue = focusedRowContent[this.state.focusedCell.columnName];

            this.setState({ copiedCell : {columnName : this.state.focusedCell.columnName, value: copiedValue}});
        }
    }

    // TODO: restrict paste to only some types of cell for cell copy
    handleCtrlVEvent = (event) =>
    {
        event.preventDefault();
        if (this.state.focusedRow in this.state.editedContent && Object.keys(this.state.copiedRow) !== 0)
        {
            let copiedContent = JSON.parse(JSON.stringify(this.state.copiedRow));
            let oldEditedContent = JSON.parse(JSON.stringify(this.state.editedContent));

            for (const column of this.columnsSchema) {
                if (column.type === "text" || column.type === "textarea" || column.type === "select" || column.type === "number" || column.type === "bool"
                    || (this.state.editedContent[this.state.focusedRow].isNewlyAdded == true && column.type == "text_editableOnlyOnAdd" ) )
                {
                    oldEditedContent[this.state.focusedRow][column.accessor] = copiedContent[column.accessor];
                }
            }

            this.setState({ editedContent : oldEditedContent });
        }
        else if (this.state.focusedCell.columnName !== null && this.state.focusedCell.rowNumber !== null)
        {
            if (this.state.copiedCell.columnName === null || !(this.state.focusedCell.rowNumber in this.state.editedContent)) {
                return;
            }

            if (this.state.copiedCell.columnName !== this.state.focusedCell.columnName) {
                return;
            }

            let copiedValue = JSON.parse(JSON.stringify(this.state.copiedCell.value));
            let editedContent = JSON.parse(JSON.stringify(this.state.editedContent));

            editedContent[this.state.focusedCell.rowNumber][this.state.focusedCell.columnName] = copiedValue;

            this.setState( {editedContent : editedContent} );

        }
    }


    handleKeyDown = (event) =>
    {
        if (!this.state.isSmartCopyEnabled) { return; }

        let charCode = String.fromCharCode(event.which).toLowerCase();

        // metaKey is for MAC users
        if((event.ctrlKey && charCode === 'c') || (event.metaKey && charCode === 'c')) {
            this.handleCtrlCEvent(event);
        }
        else if((event.ctrlKey && charCode === 'x') || (event.metaKey && charCode === 'x')) {
            this.handleCtrlXEvent(event);
        }
        else if((event.ctrlKey && charCode === 'v') || (event.metaKey && charCode === 'v')) {
            this.handleCtrlVEvent(event);
        }
    }


    revertChanges = () =>
    {
        this.refreshTable();
    }


    getAllRowsExpanded = () => {
        let expanded = {}
        let currentIndex = 0;
        this.state.data.forEach( (element) => {
            expanded[currentIndex] = true;
            ++currentIndex;
        });
        return expanded;
    }


    commitAddAndUpdateChanges = () =>
    {
        let editedContent = Object.values(this.state.editedContent);

        fetch(this.props.resourcesURLBase + 'commit', { method: 'post', body: JSON.stringify(editedContent), headers: {'Content-Type': 'application/json' } } )
        .then( (response) => response.json())
        .then( (response) =>
        {
            if (response.length === 0) {
                this.commitDeleteChanges();
            }
            else {

                let expanded = this.getAllRowsExpanded();
                this.setState({
                    expanded : expanded,
                    errorMessages : response,
                    isSmartCopyEnabled: false
                });

            }
        })
    }


    commitDeleteChanges = () =>
    {
        let deletedContent = [];
        this.state.toDelete.forEach( (item) => {
            let rowToDelete = this.state.data.find( (row) => {
                return row.tableIndex === item;
            });
            deletedContent = [...deletedContent, rowToDelete];
        });


        fetch(this.props.resourcesURLBase + 'delete', { method: 'post', body: JSON.stringify(deletedContent), headers: {'Content-Type': 'application/json' } } )
        .then( (response) =>
        {
            this.renderTable = this.renderTableInReadOnlyMode;
            document.removeEventListener("keydown", this.handleKeyDown);
            document.removeEventListener("click", this.handleClick);
            // setState here is important, do not remove it
            this.setState({
                expanded : {},
                errorMessages : []
            });
        })

    }


    continueAfterChanges = () =>
    {
        this.renderTable = this.renderTableInEditMode;
        document.addEventListener("keydown", this.handleKeyDown);
        document.addEventListener("click", this.handleClick);
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
                    getTrProps = {this.setReadonlyRowProps}
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
            else if (columnMetaData.type === "textarea")
            {
                dataColumn.minWidth = 220;
                dataColumn.Cell = this.renderTextAreaCell;
                dataColumn.Footer = this.renderTextAreaFooter;
            }
            else if (columnMetaData.type === "bool")
            {
                dataColumn.Cell = this.renderBoolCell;
                dataColumn.Footer = this.renderBoolFooter;
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
                id: 'edit',
                Cell: this.renderDeleteButton,
                sortable: false,
                filterable: false,
                resizable: false,
                Footer : ( <button onClick= { () => this.deleteMultipleRows() } > Delete selected </button> )
            },
            {
                Header : 'Edit',
                accessor: '',
                id: 'edit',
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
            <div ref = { (r) => {this.tableDiv = r} } >
                <div className = "buttonsWrapper">
                    <button onClick={ () => this.addRow() }> Add {this.props.resourceName} </button>
                    <button onClick={ () => this.commitAddAndUpdateChanges() }> Commit changes </button>
                    <button onClick={ () => this.revertChanges() }> Revert changes </button>
                    {this.renderSmartCopyButton()}
                </div>
                <ReactTable
                    ref={(r) => (this.table = r)}
                    data={this.state.data}
                    filterable
                    onFilteredChange={() => { this.handleSelectionChange(); }}
                    defaultSorted={[{
                      id   : 'tableIndex',
                      desc : false,
                    }]}
                    columns={columns}
                    getTrProps = {this.setRowProps}
                    getTdProps = {this.setCellProps}
                    expanded={this.state.expanded}
                    SubComponent = { row => {
                        let numberOfErrors = this.state.errorMessages.filter( (errorMessage) => {
                            return errorMessage.rowNumber === row.original.tableIndex;
                        }).length;
                        if (numberOfErrors === 0) {
                            return (<div/>);
                        }
                        return (
                            <div style={{ padding: '0 10px', backgroundColor : 'rgb(255,204,204)',}} >
                                <ul>
                                    {
                                        this.state.errorMessages.filter( (errorMessage) => {
                                            return errorMessage.rowNumber === row.original.tableIndex;
                                        }).map ( (errorMessage, index) => {
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