import React, { useState, useEffect } from "react";
import CustomerDataService from "../services/CustomerService";

const Detail = props => {
  const initialCustDataState = {
    name: "",
    address: "",
    city: "",
    province: "",
    registrationDate: "",
    status: ""
  };
  const [currentDataCust, setCurrentDataCust] = useState(initialCustDataState);
  
  function goToHomePage() {
    window.location.assign("/");
  }

  const getTutorial = id => {
    CustomerDataService.get(id)
      .then(response => {
        console.log(response);
        setCurrentDataCust(response.data.customerData);
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  useEffect(() => {
    getTutorial(props.match.params.id);
  }, [props.match.params.id]);

  const handleInputChange = event => {
    const { name, value } = event.target;
    setCurrentDataCust({ ...currentDataCust, [name]: value });
  };

  return (
    <div>
        <div className="detail-form">
          <h4>Detail Customer</h4>
          <form style={{width:"100%"}}>
            <div className="form-group" >
              <label htmlFor="name">Name</label>
              <input disabled 
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
              <input disabled
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
              <input disabled
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
              <input disabled
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
              <input disabled
                type="text"
                className="form-control"
                id="status"
                name="status"
                value={currentDataCust.status}
                onChange={handleInputChange}
              />
            </div>  
          </form>

          <button className="btn btn-success" onClick={goToHomePage} style= {{marginTop: "5px", marginBottom: "50px"}}>
            Backt to Homepage
          </button>
        </div>

    </div>
  );
};

export default Detail;
