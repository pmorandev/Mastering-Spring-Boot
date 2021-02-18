package com.pmoran.orderapi.validators;

import com.pmoran.orderapi.entity.Product;
import com.pmoran.orderapi.exceptions.ValidateServiceException;

public class ProductValidator {

    public static void save(Product product){
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new ValidateServiceException("Product name is required.");
        }

        if (product.getName().length() > 100) {
            throw new ValidateServiceException("Product name is to long.");
        }

        if (product.getPrice() == null) {
            throw new ValidateServiceException("Product price is required.");
        }

        if (product.getPrice() < 0) {
            throw new ValidateServiceException("Product price must be great than zero.");
        }
    }

}
