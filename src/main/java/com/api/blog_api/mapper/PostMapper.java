package com.api.blog_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.blog_api.dto.request.PostRequest;
import com.api.blog_api.dto.response.PostResponse;
import com.api.blog_api.model.PostModel;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostResponse toResponse(PostModel post);

    @Mapping(target = "id", ignore = true)
    PostModel toModel(PostRequest request);
}
