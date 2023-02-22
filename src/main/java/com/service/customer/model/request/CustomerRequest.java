package com.service.customer.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CustomerRequest {
    private String name;
    private String address;
    private String city;
    private String province;
}
