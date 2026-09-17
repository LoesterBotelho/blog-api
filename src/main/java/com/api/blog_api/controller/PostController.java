package com.api.blog_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog_api.dto.request.PostRequest;
import com.api.blog_api.dto.response.PostResponse;
import com.api.blog_api.service.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> listarTodos() {
        return postService.listarTodos();
    }

    @GetMapping("/{id}")
    public PostResponse obterPorId(@PathVariable UUID id) {
        return postService.obterPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse incluir(
            @Valid @RequestBody PostRequest request) {

        return postService.incluir(request);
    }

    @PutMapping("/{id}")
    public PostResponse atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody PostRequest request) {

        return postService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable UUID id) {
        postService.deletar(id);
    }
}