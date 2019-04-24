<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Zones Test</title>
    <script src="https://unpkg.com/react@16/umd/react.development.js"></script>
    <script src="https://unpkg.com/react-dom@16/umd/react-dom.development.js"></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>

    <link rel="stylesheet" href="https://unpkg.com/react-table@latest/react-table.css"/>

    <!-- JS -->
    <script src="https://unpkg.com/react-table@latest/react-table.js"></script>
    <script src="https://unpkg.com/react-table@latest/react-table.min.js"></script>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>

<div id = "root"></div>

<script  type = "text/babel">

    var ReactTable = window.ReactTable.default;

    class Zones extends React.Component {

        render() {
            return (
                <div>
                    <Table ref="supTable"/>
                    <Form onSubmit = {this.onFormSubmit}/>
                </div>
            );
        }

        onFormSubmit = () => {
            this.refs.supTable.refreshZonesTable();
            console.log("Form submited");
        }

    }

    const API = "http://localhost:8001/zones/all";

    class Table extends React.Component {

        constructor(props) {
            super(props);

            this.state = {
                data : [],
                selected: {}
            };
        }

        componentDidMount() {
            this.refreshZonesTable();
        }

        deleteZones = (id) => {
            self = this;
            let URI = 'http://localhost:8001/zones/delete/' + id;
            fetch(URI)
                .then(function(response) {
                    return response;
                }).then(function(data) {
                self.refreshZonesTable();
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
                    Header : "Select",
                    id : "checkbox",
                    accessor: "",
                    Cell: ({original}) => {
                        return (
                            <input
                                type = "checkbox"
                                className="checkbox"
                                checked={this.state.selected[JSON.stringify(original)] === true}
                                onChange = { () => this.toggleRow(JSON.stringify(original)) }
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
                // {
                //     Header : "Domain",
                //     accessor: 'domainname',
                // },
                {
                    Header : "Owner",
                    accessor: "owner",
                },
                {
                    Header : "Comment",
                    accessor: "comment",
                },
                {
                    Header : "Delete",
                    accessor : "",
                    Cell : ({original}) => {
                        return (
                            <button onClick={ () => {this.deleteZone(original.zoneId.id)}}> Delete </button>
                        );
                    },
                    sortable: false
                }

            ];

            return (
                <ReactTable
                    data={this.state.data}
                    columns={columns}
                    defaultSorted={ [ { id : "id", desc: true} ] }
                />
            );
        }

        refreshZonesTable() {
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

    class Form extends React.Component {

        constructor(props) {
            super(props);

            this.state = {

                zones : [

                    {
                        id : '',
                        // domainid : '',
                        // domainname : '',
                        owner : '',
                        comment : ''
                    }

                ]
            };

            this.handleNewZones = this.handleNewZones.bind(this);
        }

        addZone = (event) => {
            event.preventDefault();
            this.setState((previousState) => ( {
                zones : [... previousState.zones,
                    {
                        id : '',
                        // domainid : '',
                        // domainname : '',
                        owner : '',
                        comment : ''
                    }
                ],
            }));
        }

        handleChange = (e) => {
            let zones = [...this.state.zones];
            zones[e.target.dataset.id][e.target.className] = e.target.value;
            this.setState({zones}, () => console.log(this.state.zones));
        }

        render() {
            let {zones} = this.state;
            return (
                <form className="form-signin" style = {{width: '100%'}}>
                    <table className = "table" style = {{width: '100%'}} >
                        <tbody>
                        {
                            zones.map((value, idx) => {
                                let id = 'id-${idx}',
                                    <%--domainId= 'domainid-${idx}',--%>
                                    <%--domainName= 'domainname-${idx}',--%>
                                    owner= 'owner-${idx}',
                                    comment= 'comment-${idx}';
                                return (
                                    <tr key={idx}>
                                        <td>
                                            <input
                                                type = "text"
                                                name = {id}
                                                data-id = {idx}
                                                id = {id}
                                                value={zones[idx].id}
                                                className="id"
                                                placeholder="ID [auto]"
                                                onChange = {this.handleChange}
                                            /></td>
                                        <%--<td>--%>
                                            <%--<input--%>
                                                <%--type = "text"--%>
                                                <%--name = {domainId}--%>
                                                <%--data-id = {idx}--%>
                                                <%--id = {domainId}--%>
                                                <%--value={zones[idx].domainid}--%>
                                                <%--className="domainid"--%>
                                                <%--placeholder="Domain ID"--%>
                                                <%--onChange = {this.handleChange}--%>
                                            <%--/></td>--%>
                                        <%--<td>--%>
                                            <%--<input--%>
                                                <%--type = "text"--%>
                                                <%--name = {domainname}--%>
                                                <%--data-id = {idx}--%>
                                                <%--id = {domainname}--%>
                                                <%--value={zones[idx].domainname}--%>
                                                <%--className="domainname"--%>
                                                <%--placeholder="Domain Name"--%>
                                                <%--onChange = {this.handleChange}--%>
                                            <%--/></td>--%>
                                        <td>
                                            <input
                                                type = "text"
                                                name = {owner}
                                                data-id = {idx}
                                                id = {owner}
                                                value={zones[idx].owner}
                                                className="owner"
                                                placeholder="Owner"
                                                onChange = {this.handleChange}
                                            /></td>
                                        <td>
                                            <input
                                                type = "text"
                                                name = {comment}
                                                data-id = {idx}
                                                id = {comment}
                                                value={zones[idx].comment}
                                                className="comment"
                                                placeholder="Comment"
                                                onChange = {this.handleChange}
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
                                <button className="btn btn-lg btn-primary btn-block" onClick={(e) => this.handleNewZones(e)}>Submit</button>
                            </td>
                            <td></td><td></td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            )
        }

        handleNewZones(event) {
            event.preventDefault();
            fetch('http://localhost:8001/zones', {
                method: 'post',
                body: JSON.stringify(this.state.zones),
                headers: {'Content-Type': 'application/json'}
            }).then((response) => {
                return response;
            }).then((data) => {
                console.log('Created zones', data);
                this.setState(this.state = {

                    zones : [

                        {
                            id : '',
                            // domainid : '',
                            // domainname : '',
                            owner : '',
                            comment : ''
                        }

                    ]
                });
                this.props.onSubmit();
            });
        }

    }
    ReactDOM.render(<Zones />, document.getElementById("root"));



</script>
</body>
</html>
