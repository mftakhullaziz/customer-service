package com.service.customer.controller;

import com.service.customer.model.dto.CustomerData;
import com.service.customer.model.request.CustomerRequest;
import com.service.customer.model.response.CustomerResponse;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
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
                .thenReturn(CustomerData.builder()
                        .id(id).name(name).address(address)
                        .city(city).province(province).status(status)
                        .registrationDate(Timestamp.valueOf(dateTime))
                        .build());

        DefaultResponse response = customerRestController.createCustomer(mockRequest());
        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(response.getIsSuccess()).isEqualTo(true);
        assertThat(response.getCustomerData()).isEqualTo(mockBuildCompareResult());
    }

    private CustomerResponse mockBuildCompareResult() {
        return CustomerResponse.builder().
               id(id).name(name).address(address).
               city(city).province(province).status(status).
               registrationDate(String.valueOf(Timestamp.valueOf(dateTime))).
               build();
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