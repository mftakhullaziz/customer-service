import React, { useState, useEffect } from "react";
import CustDataService from "../services/CustomerService";

const Update = props => {
  const initialCustDataState = {
    name: "",
    address: "",
    city: "",
    province: "",
    registrationDate: "",
    status: ""
  };
  const [currentDataCust, setCurrentDataCust] = useState(initialCustDataState);
  const [message, setMessage] = useState("");

  const getCustomerDataById = id => {
    CustDataService.get(id)
      .then(response => {
        setCurrentDataCust(response.data.customerData);
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  useEffect(() => {
    getCustomerDataById(props.match.params.id);
  }, [props.match.params.id]);

  const handleInputChange = event => {
    const { name, value } = event.target;
    setCurrentDataCust({ ...currentDataCust, [name]: value });
  };

  const updateCustData = () => {
    CustDataService.update(currentDataCust.id, currentDataCust)
      .then(response => {
        console.log(response.data);
        setMessage("Update Customer successfully!");
      })
      .catch(e => {
        console.log(e);
      });
  };

  function goToHomePage() {
    window.location.assign("/");
  }

  return (
    <div>
        <div className="detail-form">
          <h4>Detail Customer</h4>
          <form style={{width:"100%"}}>
            <div className="form-group" >
              <label htmlFor="name">Name</label>
              <input 
                type="text"
                className="form-control"
                id="name"
                name="name"
                value={currentDataCust.name}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="address">Address</label>
              <input 
                type="text"
                className="form-control"
                id="address"
                name="address"
                value={currentDataCust.address}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="city">City</label>
              <input 
                type="text"
                className="form-control"
                id="city"
                name="city"
                value={currentDataCust.city}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="province">Province</label>
              <input 
                type="text"
                className="form-control"
                id="province"
                name="province"
                value={currentDataCust.province}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="registrationDate">Registration Date</label>
              <input disabled
                type="text"
                className="form-control"
                id="registrationDate"
                name="registrationDate"
                value={currentDataCust.registrationDate}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="status">Status</label>
              <input 
                type="text"
                className="form-control"
                id="status"
                name="status"
                value={currentDataCust.status}
                onChange={handleInputChange}
              />
            </div>  
          </form>

          <button className="btn btn-primary" onClick={updateCustData} style= {{marginTop: "5px", marginBottom: "20px", marginRight: "20px"}}>
            Update Customer
          </button>

          <button className="btn btn-success" onClick={goToHomePage} style= {{marginTop: "5px", marginBottom: "20px"}}>
            Backt to Homepage
          </button>

          <h3 style= {{marginTop: "5px", marginBottom: "50px"}}>{message}</h3>
        </div>

    </div>
  );
};

export default Update;
