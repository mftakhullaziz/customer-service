package com.service.customer.config;

import com.service.customer.repositories.CustomerRepository;
import com.service.customer.service.CustomerService;
import com.service.customer.service.CustomerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public CustomerService customerService(CustomerRepository repository) {
        return new CustomerServiceImpl(repository);
    }

}
