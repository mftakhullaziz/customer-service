package com.service.customer.service;

import com.service.customer.entities.CustomerEntity;
import com.service.customer.model.dto.CustomerData;
import com.service.customer.model.request.CustomerRequest;
import com.service.customer.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerRepository repository;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String province = "DKI Jakarta";
    private final String city = "Jakarta";
    private final Integer id = 1;
    private final String name = "Username";
    private final String address = "Street No.01 South Jakarta";
    private final String status = "active";
    String dateTime = Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime().format(dateTimeFormatter);

    @Test
    public void givenRequestBody_testCreateCustomer() {
        when(repository.saveAndFlush(any(CustomerEntity.class))).thenReturn(mockReturnCustomerEntity());
        // Assert
        assertThat(customerService.createCustomer(mockRequest())).usingRecursiveComparison().isEqualTo(mockReturnCustomerData());
        verify(repository, times(1)).saveAndFlush(any(CustomerEntity.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void givenRequestBodyAndId_testUpdateCustomer() {
        when(repository.findById(anyInt())).thenReturn(mockReturnOptionalCustomerEntity());
        when(repository.save(any(CustomerEntity.class))).thenReturn(mockReturnCustomerEntity());
        // Assert
        assertThat(customerService.updateCustomer(mockRequest(), anyInt())).usingRecursiveComparison().isEqualTo(mockReturnCustomerData());
        verify(repository, times(1)).findById(anyInt());
        verify(repository, times(1)).save(any(CustomerEntity.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void givenRequestId_testFindByIdCustomer() {
        when(repository.findById(anyInt())).thenReturn(mockReturnOptionalCustomerEntity());
        // Assert
        assertThat(customerService.findById(anyInt())).usingRecursiveComparison().isEqualTo(mockReturnCustomerData());
        verify(repository, times(1)).findById(anyInt());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void givenRequestStatus_testFindAllCustomer() {
        when(repository.findByStatus(anyString())).thenReturn(mockReturnListCustomerEntity());
        // Assert
        assertThat(customerService.findAllCustomer()).isEqualTo(mockReturnListCustomerData());
        verify(repository, times(1)).findByStatus(anyString());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void givenRequestName_testFindByNameCustomer() {
        when(repository.findByNameContainingIgnoreCase(anyString())).thenReturn(mockReturnListCustomerEntity());
        // Assert
        assertThat(customerService.searchCustomerByName(anyString())).usingRecursiveComparison().isEqualTo(mockReturnListCustomerData());
        verify(repository, times(1)).findByNameContainingIgnoreCase(anyString());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldDeleteToChangeStatusActivateToDeactivate_testCustomerDelete() {
        when(repository.findById(anyInt())).thenReturn(mockReturnOptionalCustomerEntity());
        when(repository.save(any(CustomerEntity.class))).thenReturn(mockReturnCustomerEntity());
        // Act & Assert
        customerService.deleteCustomer(anyInt());
        verify(repository, times(1)).findById(anyInt());
        verify(repository, times(1)).save(any(CustomerEntity.class));
        verifyNoMoreInteractions(repository);
    }

    private Optional<CustomerEntity> mockReturnOptionalCustomerEntity() {
        return Optional.ofNullable(CustomerEntity.builder().id(id)
                .name(name).address(address)
                .province(province)
                .city(city).status(status)
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

    private List<CustomerEntity> mockReturnListCustomerEntity() {
        return Collections.singletonList(
                CustomerEntity.builder()
                        .id(id).name(name).address(address)
                        .city(city).province(province).status(status)
                        .registrationDate(Timestamp.valueOf(dateTime))
                        .build());
    }

    private CustomerEntity mockReturnCustomerEntity() {
        return CustomerEntity.builder()
                        .id(id).name(name).address(address)
                        .city(city).province(province).status(status)
                        .registrationDate(Timestamp.valueOf(dateTime))
                        .build();
    }

    private List<CustomerData> mockReturnListCustomerData() {
        return Collections.singletonList(
                CustomerData.builder()
                        .id(id).name(name).address(address)
                        .city(city).province(province).status(status)
                        .registrationDate(Timestamp.valueOf(dateTime))
                        .build());
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