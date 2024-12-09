package com.ecommerce.site.Dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemDTO {
    private  Long id;
    private  Long productId;
    @Positive(message = "Quantity must be postive")
    private Integer quantity;
}
