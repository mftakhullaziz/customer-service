import React, { useState } from "react";
import CustomerDataService from "../services/CustomerService";

const Create = () => {
  const initialDataCustomerState = {
    name: "",
    address: "",
    city: "",
    province: ""
  };
  const [currentDataCust, setCreateCustData] = useState(initialDataCustomerState);
  const [message, setMessage] = useState("");

  const handleInputChange = event => {
    const { name, value } = event.target;
    setCreateCustData({ ...currentDataCust, [name]: value });
  };

  const saveCustomer = () => {
    var data = {
      name: currentDataCust.name,
      address: currentDataCust.address,
      city: currentDataCust.city,
      province: currentDataCust.province
    };

    CustomerDataService.create(data)
      .then(response => {
        setCreateCustData({
          name: response.data.customerData.name,
          address: response.data.customerData.address,
          city: response.data.customerData.city,
          province: response.data.customerData.province
        });
        setMessage("Create Customer Successfully!")
        console.log(response.data);
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
          <h4>Create Customer</h4>
          <form style={{width:"100%"}}>
            <div className="form-group" >
              <label htmlFor="name">Name</label>
              <input 
                type="text"
                className="form-control"
                id="name"
                name="name"
                required
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
                required
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
                required
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
                required
                value={currentDataCust.province}
                onChange={handleInputChange}
              />
            </div>  
          </form>

          <button onClick={saveCustomer} className="btn btn-primary" style= {{marginTop: "5px", marginBottom: "20px", marginRight: "10px"}}>
             Create Customer
          </button>

          <button className="btn btn-success" onClick={goToHomePage} style= {{marginTop: "5px", marginBottom: "20px"}}>
            Back to Homepage
          </button>

          <h3 style= {{marginTop: "5px", marginBottom: "50px"}}>{message}</h3>
        </div>

    </div>
  );
};

export default Create;
