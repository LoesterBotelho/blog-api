package com.api.blog_api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.blog_api.dto.request.PostRequest;
import com.api.blog_api.dto.response.PostResponse;
import com.api.blog_api.mapper.PostMapper;
import com.api.blog_api.model.PostModel;
import com.api.blog_api.repository.PostRepository;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Autowired
    public PostService(
            PostRepository postRepository,
            PostMapper postMapper) {

        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public List<PostResponse> listarTodos() {

        return postRepository.findAll()
                .stream()
                .map(postMapper::toResponse)
                .toList();
    }

    public PostResponse obterPorId(UUID id) {

        PostModel post = postRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Post não encontrado. Id: " + id));

        return postMapper.toResponse(post);
    }

    public PostResponse incluir(PostRequest request) {

        PostModel post = postMapper.toModel(request);

        post = postRepository.save(post);

        return postMapper.toResponse(post);
    }

    public PostResponse atualizar(
            UUID id,
            PostRequest request) {

        PostModel post = postRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Post não encontrado. Id: " + id));

        post.setAutor(request.autor());
        post.setData(request.data());
        post.setTitulo(request.titulo());
        post.setTexto(request.texto());

        post = postRepository.save(post);

        return postMapper.toResponse(post);
    }

    public void deletar(UUID id) {

        PostModel post = postRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Post não encontrado. Id: " + id));

        postRepository.delete(post);
    }
}
