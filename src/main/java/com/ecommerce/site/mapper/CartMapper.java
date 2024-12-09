package com.ecommerce.site.mapper;
import com.ecommerce.site.Dto.CartDTO;
import com.ecommerce.site.Dto.CartItemDTO;
import com.ecommerce.site.Model.Cart;
import com.ecommerce.site.Model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
//    Purpose: Converts a Cart entity into a CartDTO to expose it in API responses.
//            Importance:
//
//    Separates the database structure (Cart) from the API structure (CartDTO).
//    Helps to avoid exposing sensitive fields that might exist in the entity.
//    When to use: Use this when sending cart data to the frontend or external APIs.

    @Mapping(target = "userId",source = "user.id")
    CartDTO toDTO(Cart Cart);

//    Updated Purpose: Converts a CartDTO object back into a Cart entity.
//            Importance:
//    Supports the two-way mapping process.
//    Essential when processing incoming API data for saving or updating database records.
//    When to use: Use this method to take API request data (in DTO format) and map it to an entity for persistence or business logic.
    @Mapping(target = "user.id", source = "userId")
    Cart toEntity(CartDTO cartDTO);

//    Purpose: Converts a CartItem entity into a CartItemDTO for API responses.
//    Importance:
//
//    Encapsulates data to protect the internal structure of the CartItem entity.
//    Provides a simpler, frontend-friendly data format.
//    When to use: Use this for responses that need to include individual cart item details.
    @Mapping(target = "productId",source = "product.id")
    CartItemDTO toDTO(CartItem cartItem);

//    Purpose: Maps the productId field in the CartItemDTO back to the id field of the product object in the CartItem entity.
//    Importance:
//    Allows reconstruction of nested objects (product) from flat fields (productId).
//    When to use: When processing incoming API data in DTO format and need to save or update the CartItem entity.
    @Mapping(target = "product.id",source = "productId")
    CartItem toEntity(CartItemDTO cartItemDTO);
}
