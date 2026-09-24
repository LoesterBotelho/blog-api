package com.api.blog_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.blog_api.dto.request.CommentRequestDto;
import com.api.blog_api.dto.response.CommentResponseDto;
import com.api.blog_api.model.CommentModel;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "postId", source = "post.id")
    CommentResponseDto toResponse(CommentModel comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", ignore = true)
    CommentModel toModel(CommentRequestDto request);
}