package com.pmoran.orderapi.controllers;

import com.pmoran.orderapi.converters.OrderConverter;
import com.pmoran.orderapi.dtos.OrderDTO;
import com.pmoran.orderapi.entity.Order;
import com.pmoran.orderapi.services.OrderService;
import com.pmoran.orderapi.utils.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderConverter converter;

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<WrapperResponse<List<OrderDTO>>> findAll(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize)
    {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Order> orders = orderService.findAll(page);
        return new WrapperResponse(true, "Success",
                converter.fromEntities(orders))
                .createResponse();
    }

    @GetMapping("/orders/{orderId}")
    private ResponseEntity<WrapperResponse<OrderDTO>> findById(
            @PathVariable("orderId") Long orderId){
        Order order = orderService.findById(orderId);
        return new WrapperResponse(true,"Success",
                converter.fromEntity(order))
                .createResponse();
    }

    @PostMapping("/orders")
    private ResponseEntity<WrapperResponse<OrderDTO>> create(
            @RequestBody OrderDTO order){
        Order newOrder = orderService
                .save(converter.fromDto(order));

        return new WrapperResponse(true,"Success",
                converter.fromEntity(newOrder))
                .createResponse();
    }

    @PutMapping("/orders")
    private ResponseEntity<WrapperResponse<OrderDTO>> update(
            @RequestBody OrderDTO order){
        Order updateOrder = orderService
                .save(converter.fromDto(order));

        return new WrapperResponse(true, "Success",
                converter.fromEntity(updateOrder))
                .createResponse();
    }

    @DeleteMapping("/orders/{orderId}")
    private ResponseEntity<WrapperResponse> delete(
            @PathVariable("orderId") Long orderId){
        orderService.delete(orderId);
        return new WrapperResponse(true, "Success", null)
                .createResponse();
    }
}
