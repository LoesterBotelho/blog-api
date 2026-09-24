package com.api.blog_api.service;

import java.util.List;
import java.util.UUID;

import com.api.blog_api.dto.request.CommentRequestDto;
import com.api.blog_api.dto.response.CommentResponseDto;

public interface CommentService {
    List<CommentResponseDto> findAll();
    List<CommentResponseDto> findByPostId(UUID postId);
    CommentResponseDto findById(UUID id);
    CommentResponseDto createComment(UUID postId, CommentRequestDto dto);
    CommentResponseDto updateComment(UUID id,CommentRequestDto dto);
    void deleteComment(UUID id);
}