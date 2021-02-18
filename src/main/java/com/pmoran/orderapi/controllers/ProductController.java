package com.pmoran.orderapi.controllers;

import com.pmoran.orderapi.converters.ProductConverter;
import com.pmoran.orderapi.dtos.ProductDTO;
import com.pmoran.orderapi.entity.Product;
import com.pmoran.orderapi.services.ProductService;
import com.pmoran.orderapi.utils.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductConverter converter;

    @GetMapping("/products")
    public ResponseEntity<WrapperResponse<List<ProductDTO>>> findAll(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize)
    {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productService.findAll(page);
        return new WrapperResponse(true, "Success",
                converter.fromEntities(products))
                .createResponse();
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<WrapperResponse<ProductDTO>> findById(
            @PathVariable("productId") Long productId){
        Product product = productService.findById(productId);
        return new WrapperResponse(true, "Success",
                converter.fromEntity(product))
                .createResponse();
    }

    @PostMapping("/products")
    public ResponseEntity<WrapperResponse<ProductDTO>> create(
            @RequestBody ProductDTO product){
        Product newProduct = productService
                .save(converter.fromDto(product));
        return new WrapperResponse(true, "Success",
                converter.fromEntity(newProduct))
                .createResponse();
    }

    @PutMapping("/products")
    public ResponseEntity<WrapperResponse<ProductDTO>> update(
            @RequestBody ProductDTO product){
        Product updateProduct = productService
                .save(converter.fromDto(product));
        return new WrapperResponse(true, "Success",
                converter.fromEntity(updateProduct))
                .createResponse();
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<WrapperResponse> delete(
            @PathVariable("productId") Long productId){
        productService.delete(productId);
        return new WrapperResponse(true, "Success", null)
                .createResponse();
    }
}
