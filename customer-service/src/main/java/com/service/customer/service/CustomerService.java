package com.service.customer.service;

import com.service.customer.model.dto.CustomerData;
import com.service.customer.model.request.CustomerRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    CustomerData createCustomer(CustomerRequest request);
    CustomerData updateCustomer(CustomerRequest request, Integer id);
    CustomerData findById(Integer id);
    List<CustomerData> findAllCustomer();
    void deleteCustomer(Integer id);
    List<CustomerData> searchCustomerByName(String name);
}
