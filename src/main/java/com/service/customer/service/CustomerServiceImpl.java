package com.service.customer.service;

import com.service.customer.entities.CustomerEntity;
import com.service.customer.model.dto.CustomerData;
import com.service.customer.model.request.CustomerRequest;
import com.service.customer.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @SneakyThrows
    @Override
    public CustomerData createCustomer(CustomerRequest request) {
        CustomerEntity customerRecord = repository.saveAndFlush(constructCreateCustomer(request));
        String registrationDate = customerRecord.getRegistrationDate().toLocalDateTime().format(dateTimeFormatter);

        return CustomerData.builder().id(customerRecord.getId()).name(customerRecord.getName()).
               address(customerRecord.getAddress()).city(customerRecord.getCity()).
               province(customerRecord.getProvince()).registrationDate(Timestamp.valueOf(registrationDate)).
               status(customerRecord.getStatus()).build();
    }

    private CustomerEntity constructCreateCustomer(CustomerRequest request) {
        return CustomerEntity.builder().name(request.getName()).
               city(request.getCity()).address(request.getAddress()).
               province(request.getProvince()).status("active").build();
    }

    @Override
    public CustomerData updateCustomer(CustomerRequest request, Integer id) {
        CustomerEntity customerRecord = repository.findById(id).get();
        String registrationDate = customerRecord.getRegistrationDate().toLocalDateTime().format(dateTimeFormatter);
        customerRecord.setName(request.getName());
        customerRecord.setCity(request.getCity());
        customerRecord.setAddress(request.getAddress());
        customerRecord.setProvince(request.getProvince());
        customerRecord = repository.save(customerRecord);

        return CustomerData.builder().id(customerRecord.getId()).name(customerRecord.getName()).
               address(customerRecord.getAddress()).city(customerRecord.getCity()).
               province(customerRecord.getProvince()).registrationDate(Timestamp.valueOf(registrationDate)).
               status(customerRecord.getStatus()).build();
    }

    @Override
    public CustomerData findById(Integer id) {
        CustomerData result = null;
        Optional<CustomerEntity> customerData = repository.findById(id);
        return getCustomerData(result, customerData);
    }

    @Override
    public List<CustomerData> findAllCustomer() {
        List<CustomerData> result =  new ArrayList<>();
        List<CustomerEntity> customerRecordList = repository.findAll();
        for (CustomerEntity customerEntity : customerRecordList) {
            String registrationDate = customerEntity.getRegistrationDate().toLocalDateTime().format(dateTimeFormatter);
            CustomerData customerData = CustomerData.builder().
                    id(customerEntity.getId()).name(customerEntity.getName()).
                    address(customerEntity.getAddress()).city(customerEntity.getCity()).
                    province(customerEntity.getProvince()).registrationDate(Timestamp.valueOf(registrationDate)).
                    status(customerEntity.getStatus()).build();
            result.add(customerData);
        }
        return result;
    }

    @Override
    public void deleteCustomer(Integer id) {
        Optional<CustomerEntity> customerRecord = repository.findById(id);
        customerRecord.ifPresent(customerEntity -> {
            customerEntity.setStatus("deactivate");
            repository.save(customerEntity);
        });
    }

    @SneakyThrows
    @Override
    public List<CustomerData> searchCustomerByName(String name) {
        CustomerData resultCustomerData = null;
        List<CustomerEntity> customerData = repository.findByName(name);
        return getCustomerDataList(resultCustomerData, customerData);
    }

    private CustomerData getCustomerData(CustomerData result, Optional<CustomerEntity> customerData) {
        if (customerData.isPresent()) {
            String registrationDate = customerData.get().getRegistrationDate().toLocalDateTime().format(dateTimeFormatter);
            result = CustomerData.builder().id(customerData.get().getId()).name(customerData.get().getName()).
                    address(customerData.get().getAddress()).city(customerData.get().getCity()).
                    province(customerData.get().getProvince()).registrationDate(Timestamp.valueOf(registrationDate)).
                    status(customerData.get().getStatus()).build();
        }
        return result;
    }

    private List<CustomerData> getCustomerDataList(CustomerData result, List<CustomerEntity> customerDataList) {
        List<CustomerData> customerList = new ArrayList<>();
        for (CustomerEntity customerData : customerDataList){
            String registrationDate = customerData.getRegistrationDate().toLocalDateTime().format(dateTimeFormatter);
            result = CustomerData.builder().id(customerData.getId()).name(customerData.getName()).
                    address(customerData.getAddress()).city(customerData.getCity()).
                    province(customerData.getProvince()).registrationDate(Timestamp.valueOf(registrationDate)).
                    status(customerData.getStatus()).build();
            customerList.add(result);
        }
        return customerList;
    }

}
