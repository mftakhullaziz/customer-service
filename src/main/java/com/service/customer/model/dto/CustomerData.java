package com.service.customer.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Setter
@Getter
@Builder
public class CustomerData {
    private Integer id;
    private String name;
    private String address;
    private String city;
    private String province;
    private Timestamp registrationDate;
    private String status;
}
