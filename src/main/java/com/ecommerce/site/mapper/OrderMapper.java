package com.ecommerce.site.mapper;

import com.ecommerce.site.Dto.OrderDTO;
import com.ecommerce.site.Dto.OrderItemDTO;
import com.ecommerce.site.Model.Order;
import com.ecommerce.site.Model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "userId",source="user.id")
    @Mapping(target = "orderItems",source = "items")
    OrderDTO toDTO(Order order);

    @Mapping(target = "user.id",source="userId")
    @Mapping(target = "items",source = "orderItems")
    Order toEntity(OrderDTO orderDTO);

    List<OrderDTO> toDTOs(List<Order>orders);

    List<Order> toEntities(List<OrderDTO> orderDTOS );

    @Mapping(target = "productId",source = "product.id")
    OrderItemDTO toOrderItemsDTO(OrderItem orderItem);

    @Mapping(target = "product.id",source = "productId")
    OrderItem toOrderItemEntity(OrderItemDTO orderItemDTO);

    List<OrderItemDTO> toOrderItemsDTOs(List<OrderItem>orderItem);

    List<OrderItem>toOrderItemEntities(List<OrderItemDTO>orderItemDTOS);
}
