package com.pmoran.orderapi.converters;

import com.pmoran.orderapi.dtos.OrderDTO;
import com.pmoran.orderapi.dtos.OrderLineDTO;
import com.pmoran.orderapi.entity.Order;
import com.pmoran.orderapi.entity.OrderLine;
import lombok.AllArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OrderConverter extends AbstractConverter<Order, OrderDTO> {

    private DateTimeFormatter formatter;
    private ProductConverter productConverter;
    private UserConverter userConverter;

    @Override
    public Order fromDto(OrderDTO dto) {
        if (dto==null) return null;

        List<OrderLine> lines = fromOrderLinesDTO(dto.getLines());

        return Order.builder()
                .id(dto.getId())
                .lines(lines)
                .user(userConverter.fromDto(dto.getUser()))
                .total(dto.getTotal())
                .build();
    }

    @Override
    public OrderDTO fromEntity(Order entity) {
        if (entity==null) return null;

        List<OrderLineDTO> lines = fromOrderLinesEntity(entity.getLines());

        return OrderDTO.builder()
                .id(entity.getId())
                .regDate(entity.getRegDate().format(formatter))
                .lines(lines)
                .user(userConverter.fromEntity(entity.getUser()))
                .total(entity.getTotal())
                .build();
    }

    private List<OrderLineDTO> fromOrderLinesEntity(List<OrderLine> lines){
        if(lines==null) return null;

        return lines.stream().map(line -> {
                    return OrderLineDTO.builder()
                            .id(line.getId())
                            .price(line.getPrice())
                            .product(productConverter.fromEntity(line.getProduct()))
                            .quantity(line.getQuantity())
                            .total(line.getTotal())
                            .build();
                }
        ).collect(Collectors.toList());
    }

    private List<OrderLine> fromOrderLinesDTO(List<OrderLineDTO> lines){
        if(lines==null) return null;

        return lines.stream().map(line -> {
                    return OrderLine.builder()
                            .id(line.getId())
                            .price(line.getPrice())
                            .product(productConverter.fromDto(line.getProduct()))
                            .quantity(line.getQuantity())
                            .total(line.getTotal())
                            .build();
                }
        ).collect(Collectors.toList());
    }
}
