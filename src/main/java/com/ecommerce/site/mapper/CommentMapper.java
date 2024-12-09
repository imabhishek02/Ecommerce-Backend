package com.ecommerce.site.mapper;

import com.ecommerce.site.Dto.CommentDTO;
import com.ecommerce.site.Model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "userId",source = "user.id")
    CommentDTO toDTO(Comment comment);

//    When to Use ignore = true:
//
//    When a field should not be mapped: You may have a field in the DTO (like product) that you do not want to persist or send back to the entity. This is a good use case for ignore = true.
//    When dealing with optional fields: If a field is optional or not always needed for persistence (like product in this case), you can ignore it.
    @Mapping(target = "user.id",source = "userId")
    @Mapping(target = "product",ignore = true)
    Comment toEntity(CommentDTO commentDTO);
}
