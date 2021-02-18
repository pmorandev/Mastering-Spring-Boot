package com.pmoran.orderapi.converters;

import com.pmoran.orderapi.dtos.ProductDTO;
import com.pmoran.orderapi.entity.Product;

public class ProductConverter extends AbstractConverter<Product, ProductDTO> {

    @Override
    public Product fromDto(ProductDTO dto) {
        if(dto==null) return  null;

        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .build();
    }

    @Override
    public ProductDTO fromEntity(Product entity) {
        if(entity==null) return  null;

        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .build();
    }
}
