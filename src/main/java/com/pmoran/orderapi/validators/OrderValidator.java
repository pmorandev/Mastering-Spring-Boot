package com.pmoran.orderapi.validators;

import com.pmoran.orderapi.entity.Order;
import com.pmoran.orderapi.entity.OrderLine;
import com.pmoran.orderapi.exceptions.ValidateServiceException;

public class OrderValidator {

    public static void save(Order order){
        if (order.getLines() == null){
            throw new ValidateServiceException("Product lines are required.");
        }

        if (order.getLines().isEmpty()){
            throw new ValidateServiceException("Product lines are required.");
        }

        for(OrderLine line : order.getLines()){
            if (line.getProduct() == null
                    || line.getProduct().getId() == null){
                throw new ValidateServiceException("Product is required.");
            }

            if (line.getQuantity() == null
                    || line.getQuantity() < 0){
                throw new ValidateServiceException("Product quantity is required.");
            }
        }
    }

}
