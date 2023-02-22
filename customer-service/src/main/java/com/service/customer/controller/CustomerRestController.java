package com.service.customer.controller;

import com.service.customer.model.dto.CustomerData;
import com.service.customer.model.request.CustomerRequest;
import com.service.customer.model.response.CustomerResponse;
import com.service.customer.model.response.DefaultListResponse;
import com.service.customer.model.response.DefaultResponse;
import com.service.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/internal/api/")
@RequiredArgsConstructor
public class CustomerRestController {

    private final CustomerService service;

    private final LocalDateTime localDateTime = LocalDateTime.now();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // API Create Customer
    @PostMapping("createCustomer")
    private DefaultResponse createCustomer(@Valid @RequestBody CustomerRequest request) {
        CustomerData customerData = service.createCustomer(request);
        CustomerResponse customerResponse =  constructCustomerResponse(customerData);
        return constructCreateOrUpdateOrFindByIdCustomerResponse(customerResponse);
    }

    // API Update Customer
    @PutMapping("updateCustomer/{id}")
    private DefaultResponse updateCustomer(@PathVariable Integer id,
                                           @Valid @RequestBody CustomerRequest request) {
        CustomerData customerData = service.updateCustomer(request, id);
        CustomerResponse customerResponse = constructCustomerResponse(customerData);
        return constructCreateOrUpdateOrFindByIdCustomerResponse(customerResponse);
    }

    // API Find Customer By Id
    @GetMapping("findCustomer/{id}")
    public DefaultResponse findCustomerById(@PathVariable Integer id) {
        CustomerData customerData = service.findById(id);
        CustomerResponse customerResponse = constructCustomerResponse(customerData);
        return constructCreateOrUpdateOrFindByIdCustomerResponse(customerResponse);
    }

    // API Find All
    @GetMapping("findCustomer")
    public DefaultListResponse findCustomerAll() {
        List<CustomerData> customerDataList = service.findAllCustomer();
        List<CustomerResponse> customerResponseList = constructListAndSearchByNameCustomerResponse(customerDataList);
        return constructListCustomerResponse(customerResponseList);
    }

    // API Delete Customer
    @DeleteMapping("deleteCustomer/{id}")
    public DefaultResponse deleteCustomerById(@PathVariable Integer id) {
        service.deleteCustomer(id);
        return DefaultResponse.builder().statusCode(200).
               message("delete with id customer = " + id + " is success!").
               datetimeRequest(localDateTime.format(dateTimeFormatter)).
               isSuccess(Boolean.TRUE).customerData(null).build();
    }

    // API Find Customer By Name
    @GetMapping("findCustomerByName")
    public DefaultListResponse findCustomerByName(@RequestParam(defaultValue = "test") String name) {
        List<CustomerData> customerData = service.searchCustomerByName(name);
        List<CustomerResponse> customerResponse = constructListAndSearchByNameCustomerResponse(customerData);
        return constructListCustomerResponse(customerResponse);
    }

    // Construct Customer Response
    private CustomerResponse constructCustomerResponse(CustomerData customerData) {
        return CustomerResponse.builder().id(customerData.getId()).name(customerData.getName()).
                address(customerData.getAddress()).city(customerData.getCity()).
                province(customerData.getProvince()).registrationDate(String.valueOf(customerData.getRegistrationDate())).
                status(customerData.getStatus()).build();
    }

    // Construct to Build Result of Create Customer Response
    private DefaultResponse constructCreateOrUpdateOrFindByIdCustomerResponse(CustomerResponse customerResponse) {
        return DefaultResponse.builder().statusCode(201).message("API Request Customer Success!").
                isSuccess(Boolean.TRUE).customerData(customerResponse).
                datetimeRequest(localDateTime.format(dateTimeFormatter)).build();
    }

    // Construct List Customer Response
    private List<CustomerResponse> constructListAndSearchByNameCustomerResponse(List<CustomerData> customerDataList) {
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        for (CustomerData customerData : customerDataList) {
            CustomerResponse customerResponse = CustomerResponse.builder().id(customerData.getId()).
                    name(customerData.getName()).address(customerData.getAddress()).
                    city(customerData.getCity()).province(customerData.getProvince()).
                    registrationDate(String.valueOf(customerData.getRegistrationDate())).
                    status(customerData.getStatus()).build();
            customerResponseList.add(customerResponse);
        }
        return customerResponseList;
    }

    // Construct to Build Result of Create Customer Response
    private DefaultListResponse constructListCustomerResponse(List<CustomerResponse> customerResponse) {
        return DefaultListResponse.builder().statusCode(201).message("Create Customer Success!").
               isSuccess(Boolean.TRUE).customerData(customerResponse).
               datetimeRequest(localDateTime.format(dateTimeFormatter)).build();
    }

}
