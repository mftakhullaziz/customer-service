package com.service.customer.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Builder
public class DefaultResponse {
    private Integer statusCode;
    private String message;
    private Boolean isSuccess;
    private String datetimeRequest;
    private CustomerResponse customerData;
}
