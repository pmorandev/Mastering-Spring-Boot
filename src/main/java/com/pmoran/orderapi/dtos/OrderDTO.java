package com.pmoran.orderapi.dtos;

import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private String regDate;
    private List<OrderLineDTO> lines;
    private UserDTO user;
    private Double total;
}
