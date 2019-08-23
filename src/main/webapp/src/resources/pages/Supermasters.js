import React from "react";
import "../styles/Supermasters.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "react-table/react-table.css";
import ReusableTable from "../components/ReusableTable.js"

class Supermasters extends React.Component {

    render()
    {
        let emptyDataExample = { ip : '', nameserver : null, account : null };
        const columns = [
            { Header : "IP", accessor : "ip", type: "text_editableOnlyOnAdd" },
            { Header : "Nameserver", accessor : "nameserver", type: "text_editableOnlyOnAdd" },
            { Header : "Account", accessor : "account", type: "text" },
        ];
        return (
            <div className="supermasters">
                <ReusableTable ref = "supermastersTable"
                fetchValueConstraints = {() => {}}
                resourcesURLBase = "http://localhost:8001/supermasters/"
                resourcesSelectURL = "all"
                emptyDataExample = {emptyDataExample}
                columns = {columns}
                resourceName = "supermaster" />
            </div>
        );
    }

}
export default Supermasters;
