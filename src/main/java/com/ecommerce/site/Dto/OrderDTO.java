package com.ecommerce.site.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import com.ecommerce.site.Model.Order;
@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    @NotBlank(message = "Address cannot be blank")
    private String address;
    @NotBlank(message = "phoneNumber cannot be blank")
    private String phoneNumber;
    private Order.OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> orderItems;
}
