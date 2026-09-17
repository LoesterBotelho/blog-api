package com.api.blog_api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.blog_api.dto.request.PostRequestDto;
import com.api.blog_api.dto.response.PostResponseDto;
import com.api.blog_api.mapper.PostMapper;
import com.api.blog_api.model.PostModel;
import com.api.blog_api.repository.PostRepository;

@Service
public class PostServiceImpl {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Autowired
    public PostServiceImpl(
            PostRepository postRepository,
            PostMapper postMapper) {

        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public List<PostResponseDto> listarTodos() {

        return postRepository.findAll()
                .stream()
                .map(postMapper::toResponse)
                .toList();
    }

    public PostResponseDto obterPorId(UUID id) {

        PostModel post = postRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Post não encontrado. Id: " + id));

        return postMapper.toResponse(post);
    }

    public PostResponseDto incluir(PostRequestDto request) {

        PostModel post = postMapper.toModel(request);

        post = postRepository.save(post);

        return postMapper.toResponse(post);
    }

    public PostResponseDto atualizar(
            UUID id,
            PostRequestDto request) {

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
