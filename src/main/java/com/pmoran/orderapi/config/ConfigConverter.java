package com.pmoran.orderapi.config;

import com.pmoran.orderapi.converters.OrderConverter;
import com.pmoran.orderapi.converters.ProductConverter;
import com.pmoran.orderapi.converters.UserConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class ConfigConverter {

    @Value("${config.datetimeformat}")
    private String formatDate;

    @Bean
    public ProductConverter getProductConverter(){
        return new ProductConverter();
    }

    @Bean
    public OrderConverter getOrderConverter(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern(formatDate);
        return new OrderConverter(format, getProductConverter(), getUserConverter());
    }

    @Bean
    public UserConverter getUserConverter(){
        return new UserConverter();
    }

}
