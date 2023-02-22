import http from "../http-common";

const getAll = () => {
  return http.get("http://localhost:9000/internal/api/findCustomer");
};

const get = (id) => {
  return http.get(`http://localhost:9000/internal/api/findCustomer/${id}`);
};

const create = (data) => {
  return http.post("http://localhost:9000/internal/api/createCustomer", data);
};

const update = (id, data) => {
  return http.put(`http://localhost:9000/internal/api/updateCustomer/${id}`, data);
};

const remove = (id) => {
  return http.delete(`http://localhost:9000/internal/api/deleteCustomer/${id}`);
};

const findByCustomerName = (title) => {
  return http.get(`http://localhost:9000/internal/api/findCustomerByName?name=${title}`);
};

const CustomerService = {
  getAll,
  get,
  create,
  update,
  remove,
  findByCustomerName,
};

export default CustomerService;
