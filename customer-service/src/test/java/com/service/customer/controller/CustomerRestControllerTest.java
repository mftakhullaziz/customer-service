package com.service.customer.controller;

import com.service.customer.model.dto.CustomerData;
import com.service.customer.model.request.CustomerRequest;
import com.service.customer.model.response.CustomerResponse;
import com.service.customer.model.response.DefaultListResponse;
import com.service.customer.model.response.DefaultResponse;
import com.service.customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerRestControllerTest {

    @InjectMocks
    CustomerRestController customerRestController;

    @Mock
    CustomerService service;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String province = "DKI Jakarta";
    private final String city = "Jakarta";
    private final Integer id = 1;
    private final String name = "Username";
    private final String address = "Street No.01 South Jakarta";
    private final String status = "active";
    String dateTime = Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime().format(dateTimeFormatter);

    @Test
    public void givenCustomerRequest_testCreateCustomer() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(service.createCustomer(any(CustomerRequest.class)))
                .thenReturn(mockReturnCustomerData());
        DefaultResponse response = customerRestController.createCustomer(mockRequest());
        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(response.getIsSuccess()).isEqualTo(true);
        assertThat(response.getCustomerData()).isEqualTo(mockBuildCompareResult());
    }

    @Test
    public void givenIdRequest_testFindByIdCustomer() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(service.findById(anyInt())).thenReturn(mockReturnCustomerData());
        DefaultResponse response = customerRestController.findCustomerById(id);
        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(response.getIsSuccess()).isEqualTo(true);
        assertThat(response.getCustomerData()).isEqualTo(mockBuildCompareResult());
    }

    @Test
    public void givenNameRequest_testFindByNameCustomer() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(service.searchCustomerByName(anyString())).thenReturn(
                mockReturnListCustomerData()
        );
        DefaultListResponse response = customerRestController.findCustomerByName(name);
        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(response.getIsSuccess()).isEqualTo(true);
        assertThat(response.getCustomerData()).isEqualTo(mockBuildCompareListResult());
    }

    @Test
    public void givenAll_testFindAllCustomer() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(service.findAllCustomer()).thenReturn(
                mockReturnListCustomerData()
        );
        DefaultListResponse response = customerRestController.findCustomerAll();
        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(response.getIsSuccess()).isEqualTo(true);
        assertThat(response.getCustomerData()).isEqualTo(mockBuildCompareListResult());
    }

    @Test
    public void givenIdAndRequest_testUpdateCustomer() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(service.updateCustomer(mockRequest(), id)).thenReturn(
                mockReturnCustomerData()
        );
        DefaultResponse response = customerRestController.updateCustomer(id, mockRequest());
        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(response.getIsSuccess()).isEqualTo(true);
        assertThat(response.getCustomerData()).isEqualTo(mockBuildCompareResult());
    }

    @Test
    public void givenId_testDeleteCustomerById() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        service.deleteCustomer(id);
        verify(service).deleteCustomer(id);
    }

    private CustomerResponse mockBuildCompareResult() {
        return CustomerResponse.builder().
               id(id).name(name).address(address).
               city(city).province(province).status(status).
               registrationDate(String.valueOf(Timestamp.valueOf(dateTime))).
               build();
    }

    private List<CustomerResponse> mockBuildCompareListResult() {
        return Collections.singletonList(CustomerResponse.builder().
                id(id).name(name).address(address).
                city(city).province(province).status(status).
                registrationDate(String.valueOf(Timestamp.valueOf(dateTime))).
                build());
    }

    private List<CustomerData> mockReturnListCustomerData() {
        return Collections.singletonList(
                CustomerData.builder()
                        .id(id).name(name).address(address)
                        .city(city).province(province).status(status)
                        .registrationDate(Timestamp.valueOf(dateTime))
                        .build());
    }

    private CustomerData mockReturnCustomerData() {
        return CustomerData.builder()
                .id(id).name(name).address(address)
                .city(city).province(province).status(status)
                .registrationDate(Timestamp.valueOf(dateTime))
                .build();
    }

    private CustomerRequest mockRequest() {
        CustomerRequest request = new CustomerRequest();
        request.setName(name);
        request.setCity(city);
        request.setAddress(address);
        request.setProvince(province);
        return request;
    }
}