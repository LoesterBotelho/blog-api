package com.api.blog_api.service;

import java.util.List;
import java.util.UUID;

import com.api.blog_api.dto.request.PostRequestDto;
import com.api.blog_api.dto.response.PostResponseDto;

public interface PostService {

    List<PostResponseDto> findAll();

    PostResponseDto findById(UUID id);

    PostResponseDto createPost(PostRequestDto dto);

    PostResponseDto updatePost(
            UUID id,
            PostRequestDto dto
    );

    void deletePost(UUID id);
}
