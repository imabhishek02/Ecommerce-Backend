package com.ecommerce.site.mapper;

import com.ecommerce.site.Dto.CommentDTO;
import com.ecommerce.site.Dto.ProductDTO;
import com.ecommerce.site.Model.Comment;
import com.ecommerce.site.Model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    @Mapping(target = "image",source = "image")
    ProductDTO toDTO(Product product);

    @Mapping(target = "image",source = "image")
    Product toEntity(ProductDTO productDTO);

    @Mapping(target = "userId",source = "user.id")
    CommentDTO toDTO(Comment comment);

    @Mapping(target = "user.id",source = "userId")
    @Mapping(target = "product",ignore = true)
    Comment toEntity(CommentDTO commentDTO);
}
