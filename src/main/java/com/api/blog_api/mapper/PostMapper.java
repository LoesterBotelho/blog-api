package com.api.blog_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.blog_api.dto.request.PostRequestDto;
import com.api.blog_api.dto.response.PostResponseDto;
import com.api.blog_api.model.PostModel;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface PostMapper {

    PostResponseDto toResponse(PostModel post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    PostModel toModel(PostRequestDto request);
}