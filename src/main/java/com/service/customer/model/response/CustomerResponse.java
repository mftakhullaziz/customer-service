package com.service.customer.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Builder
public class CustomerResponse {
    private Integer id;
    private String name;
    private String address;
    private String city;
    private String province;
    private String registrationDate;
    private String status;
}
