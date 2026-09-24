package com.api.blog_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog_api.dto.request.CommentRequestDto;
import com.api.blog_api.dto.response.CommentResponseDto;
import com.api.blog_api.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable UUID postId,
            @Valid @RequestBody CommentRequestDto dto) {

        CommentResponseDto comment = commentService.createComment(postId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> findByPostId(@PathVariable UUID postId) {
        return ResponseEntity.ok(commentService.findByPostId(postId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable UUID id,
            @Valid @RequestBody CommentRequestDto dto) {

        return ResponseEntity.ok(commentService.updateComment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}