package com.service.customer.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
@Builder
public class DefaultListResponse {
    private Integer statusCode;
    private String message;
    private Boolean isSuccess;
    private String datetimeRequest;
    private List<CustomerResponse> customerData;
}
