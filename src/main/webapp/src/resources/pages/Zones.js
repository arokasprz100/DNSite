import React from "react";
import "../styles/Zones.css";
import "bootstrap/dist/css/bootstrap.min.css";
import ReactTable from "react-table";
import "react-table/react-table.css";

class Zones extends React.Component {
  render() {
    return (
      <div class="Table">
        <Table ref="supTable" />
        <Form onSubmit={this.onFormSubmit} />
      </div>
    );
  }

  onFormSubmit = () => {
    this.refs.supTable.refreshZonesTable();
    console.log("Form submited");
  };
}

const API = "http://localhost:8001/zones/all";

class Table extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      data: [],
      selected: {}
    };
  }

  componentDidMount() {
    this.refreshZonesTable();
  }

  deleteZones = id => {
    let self = this;
    let URI = "http://localhost:8001/zones/delete/" + id;
    fetch(URI)
      .then(function(response) {
        return response;
      })
      .then(function(data) {
        self.refreshZonesTable();
      });
  };

  toggleRow(zoneId) {
    console.log(this.state.selected);
    const newSelected = Object.assign({}, this.state.selected);
    newSelected[zoneId] = !this.state.selected[zoneId];
    this.setState({
      selected: newSelected
    });
  }

  renderTable() {
    const columns = [
      {
        Header: "Select",
        id: "checkbox",
        accessor: "",
        Cell: ({ original }) => {
          return (
            <input
              type="checkbox"
              className="checkbox"
              checked={this.state.selected[JSON.stringify(original)] === true}
              onChange={() => this.toggleRow(JSON.stringify(original))}
            />
          );
        },
        sortable: false,
        width: 45
      },
      {
        Header: "ID",
        accessor: "id"
      },
      {
        Header: "Domain ID",
        accessor: "domain.id"
      },
      {
        Header: "Domain Name",
        accessor: "domain.name"
      },
      {
        Header: "Owner",
        accessor: "owner"
      },
      {
        Header: "Comment",
        accessor: "comment"
      },
      {
        /*
            <%--{--%>
            <%--    Header : "Delete",--%>
            <%--    accessor : "",--%>
            <%--    Cell : ({original}) => {--%>
            <%--        return (--%>
            <%--            <button onClick={ () => {this.deleteZone(original.zoneId.id)}}> Delete </button>--%>
            <%--        );--%>
            <%--    },--%>
            <%--    sortable: false--%>
            <%--}--%>
            */
      }
    ];

    return (
      <ReactTable
        data={this.state.data}
        columns={columns}
        defaultSorted={[{ id: "id", desc: true }]}
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
        console.log(data);
        this.setState({ data: data, selected: {} });
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
      zones: [
        {
          id: "",
          domain: {
            id: "",
            name: ""
          },
          owner: "",
          comment: ""
        }
      ]
    };

    this.handleNewZones = this.handleNewZones.bind(this);
  }

  addZone = event => {
    event.preventDefault();
    this.setState(previousState => ({
      zones: [
        ...previousState.zones,
        {
          id: "",
          domain: {
            id: "",
            name: ""
          },
          owner: "",
          comment: ""
        }
      ]
    }));
  };

  handleChange = e => {
    let zones = [...this.state.zones];
    if (e.target.className == "domainid") {
      zones[e.target.dataset.id]["domain"].id = e.target.value;
    } else if (e.target.className == "domainname") {
      zones[e.target.dataset.id]["domain"].name = e.target.value;
    } else {
      zones[e.target.dataset.id][e.target.className] = e.target.value;
    }
    this.setState({ zones }, () => console.log(this.state.zones));
  };

  render() {
    let { zones } = this.state;
    return (
      <form className="form-signin" style={{ width: "100%" }}>
        <table className="table" style={{ width: "100%" }}>
          <tbody>
            {zones.map((value, idx) => {
              let id = "id-${idx}",
                domainId = "domainid-${idx}",
                domainName = "domainname-${idx}",
                owner = "owner-${idx}",
                comment = "comment-${idx}";
              return (
                <tr key={idx}>
                  <td>
                    <input
                      type="text"
                      name={id}
                      data-id={idx}
                      id={id}
                      value={zones[idx].id}
                      className="id"
                      placeholder="ID [auto]"
                      onChange={this.handleChange}
                    />
                  </td>
                  <td>
                    <input
                      type="text"
                      name={domainId}
                      data-id={idx}
                      id={domainId}
                      value={zones[idx].domain.id}
                      className="domainid"
                      placeholder="Domain ID"
                      onChange={this.handleChange}
                    />
                  </td>
                  <td>
                    <input
                      type="text"
                      name={domainName}
                      data-id={idx}
                      id={domainName}
                      value={zones[idx].domain.name}
                      className="domainname"
                      placeholder="Domain Name"
                      onChange={this.handleChange}
                    />
                  </td>
                  <td>
                    <input
                      type="text"
                      name={owner}
                      data-id={idx}
                      id={owner}
                      value={zones[idx].owner}
                      className="owner"
                      placeholder="Owner"
                      onChange={this.handleChange}
                    />
                  </td>
                  <td>
                    <input
                      type="text"
                      name={comment}
                      data-id={idx}
                      id={comment}
                      value={zones[idx].comment}
                      className="comment"
                      placeholder="Comment"
                      onChange={this.handleChange}
                    />
                  </td>
                </tr>
              );
            })}
            <tr>
              <td>
                <button
                  className="btn btn-lg btn-primary btn-block"
                  onClick={this.addZone}
                >
                  {" "}
                  Add new zone{" "}
                </button>
              </td>
              <td>
                <button
                  className="btn btn-lg btn-primary btn-block"
                  onClick={e => this.handleNewZones(e)}
                >
                  Submit
                </button>
              </td>
              <td />
              <td />
              <td />
            </tr>
          </tbody>
        </table>
      </form>
    );
  }

  handleNewZones(event) {
    event.preventDefault();
    fetch("http://localhost:8001/zones", {
      method: "post",
      body: JSON.stringify(this.state.zones),
      headers: { "Content-Type": "application/json" }
    })
      .then(response => {
        return response;
      })
      .then(data => {
        console.log("Created zones", data);
        this.setState(
          (this.state = {
            zones: [
              {
                id: "",
                domain: {
                  id: "",
                  name: ""
                },
                owner: "",
                comment: ""
              }
            ]
          })
        );
        this.props.onSubmit();
      });
  }
}

export default Zones;
