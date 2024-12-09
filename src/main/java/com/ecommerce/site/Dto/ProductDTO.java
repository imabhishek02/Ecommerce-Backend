package com.ecommerce.site.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data

public class ProductDTO {
    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    private String name;
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @Positive(message = "Price cannot be negative")
    private BigDecimal price;
    @PositiveOrZero(message = "Quantity cannot be negative")
    private Integer quantity;
    private String image;
    private List<CommentDTO> comments;

}
