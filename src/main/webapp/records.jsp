<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Log in with your account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->


    <script src="https://unpkg.com/react@16/umd/react.development.js"></script>
    <script src="https://unpkg.com/react-dom@16/umd/react-dom.development.js"></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>

    <link rel="stylesheet" href="https://unpkg.com/react-table@latest/react-table.css"/>

    <!-- JS -->
    <script src="https://unpkg.com/react-table@latest/react-table.js"></script>
    <script src="https://unpkg.com/react-table@latest/react-table.min.js"></script>

</head>

<body>

    <div id = "root"></div>

    <script type="text/babel">

        const API = "http://localhost:8001/records/all";

        var ReactTable = window.ReactTable.default;

        class RecordsTable extends React.Component {

            constructor(props) {
                super(props);

                this.state = {
                    data : [],
                    selected: {}
                };
            }

            componentDidMount() {
                this.refreshRecordsTable();
            }

            toggleRow(recordId) {
                console.log(this.state.selected);
                const newSelected = Object.assign( {}, this.state.selected);
                newSelected[recordId] = !this.state.selected[recordId];
                this.setState({
                    selected: newSelected,
                });
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
                    Header : "Select",
                    id : "checkbox",
                    accessor: "",
                    Cell: ({original}) => {
                        return (
                            <input
                                type = "checkbox"
                                className="checkbox"
                                checked={this.state.selected[JSON.stringify(original.id)] === true}
                                onChange = { () => this.toggleRow(JSON.stringify(original.id)) }
                            />
                        );
                    },
                    sortable: false,
                    width: 45
                },
                {
                    Header : "ID",
                    accessor: 'id',
                },
                {
                    Header : "Domain",
                    accessor: 'domain',
                    Cell: this.renderEditable
                },
                {
                    Header : 'Name',
                    accessor: 'name',
                    Cell: this.renderEditable
                },
                {
                    Header : 'Type',
                    accessor: 'type',
                    Cell: this.renderEditable
                },
                {
                    Header : 'Content',
                    accessor: 'content',
                    Cell: this.renderEditable
                },
                {
                    Header : 'TTL',
                    accessor: 'ttl',
                    Cell: this.renderEditable
                }
                ];

                return (
                    <ReactTable
                        data={this.state.data}
                        columns={columns}
                    />
                );
            }

            refreshRecordsTable() {
                fetch(API)
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

            render() {
                return this.renderTable();
            }
        }

        ReactDOM.render(<RecordsTable />, document.getElementById("root"));

    </script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
