import React, { useState, useEffect, useMemo, useRef } from "react";
import CustomerDataService from "../services/CustomerService";
import { useTable } from "react-table";

const DataCustomerList = (props) => {
  const [customers, setCustomers] = useState([]);
  const [searchCustomer, setSearchCustomer] = useState("");
  const customerRef = useRef();

  customerRef.current = customers;

  useEffect(() => {
    retrieveCustomer();
  }, []);

  const onChangeSearchCustomer = (e) => {
    const searchCust = e.target.value;
    setSearchCustomer(searchCust);
  };

  const retrieveCustomer = () => {
    CustomerDataService.getAll()
      .then((response) => {
        console.log(response);
        setCustomers(response.data.customerData);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const refreshList = () => {
    retrieveCustomer();
  };

  const findByCustomerName = () => {
    CustomerDataService.findByCustomerName(searchCustomer)
      .then((response) => {
        setCustomers(response.data.customerData);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  function goToPage() {
    window.location.assign("/add");
  }
  

  const gotoUpdate = (rowIndex) => {
    const id = customerRef.current[rowIndex].id;
    props.history.push("/updates/" + id);
  };

  const detailCustomers = (rowIndex) => {
    const id = customerRef.current[rowIndex].id;
    props.history.push("/details/" + id);
  };

  const deleteCustomer = (rowIndex) => {
    const id = customerRef.current[rowIndex].id;

    CustomerDataService.remove(id)
      .then((response) => {
        props.history.push("/homepage");

        let newCustomers = [...customerRef.current];
        newCustomers.splice(rowIndex, 1);

        setCustomers(newCustomers);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const columns = useMemo(
    () => [
      {
        Header: "ID",
        accessor: "id",
      },
      {
        Header: "Name",
        accessor: "name",
      },
      {
        Header: "Address",
        accessor: "address",
      },
      {
        Header: "City",
        accessor: "city",
      },
      {
        Header: "Province",
        accessor: "province",
      },
      {
        Header: "Registration Date",
        accessor: "registrationDate",
      },
      {
        Header: "Status",
        accessor: "status",
      },
      {
        Header: "Actions",
        accessor: "actions",
        Cell: (props) => {
          const rowIdx = props.row.id;
          return (
            <div style={{ margin : "10px"}}>
              <button className="btn btn-sm btn-primary" onClick={() => detailCustomers(rowIdx)}
               style = {{float : "left", fontSize: "14px", marginRight: "5px"}}
              >
                detail
              </button>

              <button className="btn btn-sm btn-primary" onClick={() => gotoUpdate(rowIdx)}
               style = {{float : "left", fontSize: "14px"}}
              >
                update
              </button>
              
              <button className="btn btn-sm btn-primary" onClick={() => deleteCustomer(rowIdx)} 
               style = {{float : "right", fontSize: "14px"}}
              >
                delete
              </button>
            </div>
          );
        },
      },
    ],
    []
  );

  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    rows,
    prepareRow,
  } = useTable({
    columns,
    data: customers,
  });

  return (
    <div className="list row">
      <div className="col-8 col-md-4">
        <button className="btn-lg btn-sm btn-primary" onClick={goToPage}
         style= {{
            fontSize: "18px",
            fontWeight: "normal"
         }}>
          Create Customer
        </button>
      </div>
      <div className="col-12 col-md-8">
        <div className="input-group mb-3">
          <input
            type="text"
            className="form-control"
            placeholder="Search by name"
            value={searchCustomer}
            onChange={onChangeSearchCustomer}
            style= {{
              fontSize: "18px",
              fontWeight: "normal"
            }}
          />
          <div className="input-group-append">
            <button
              className="btn btn-primary"
              type="button"
              onClick={findByCustomerName}
            >
              Search
            </button>
          </div>
        </div>
      </div>
      <div className="col-md-12 list">
        <table
          className="table table-striped table-bordered"
          {...getTableProps()}
        >
          <thead>
            {headerGroups.map((headerGroup) => (
              <tr {...headerGroup.getHeaderGroupProps()}>
                {headerGroup.headers.map((column) => (
                  <th {...column.getHeaderProps()}>
                    {column.render("Header")}
                  </th>
                ))}
              </tr>
            ))}
          </thead>
          <tbody {...getTableBodyProps()}>
            {rows.map((row, i) => {
              prepareRow(row);
              return (
                <tr {...row.getRowProps()}>
                  {row.cells.map((cell) => {
                    return (
                      <td {...cell.getCellProps()}>{cell.render("Cell")}</td>
                    );
                  })}
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default DataCustomerList;
