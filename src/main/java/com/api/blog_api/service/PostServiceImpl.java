package com.api.blog_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.blog_api.dto.request.PostRequestDto;
import com.api.blog_api.dto.response.PostResponseDto;
import com.api.blog_api.exception.RegistroNaoEncontradoException;
import com.api.blog_api.mapper.PostMapper;
import com.api.blog_api.model.PostModel;
import com.api.blog_api.repository.PostRepository;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Autowired
    public PostServiceImpl(
            PostRepository postRepository,
            PostMapper postMapper) {

        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    @Transactional
    public PostResponseDto createPost(PostRequestDto dto) {

        PostModel post = postMapper.toModel(dto);

        // Garante que a data atual seja definida antes de salvar caso o mapper não a preencha
        if (post.getData() == null) {
            post.setData(LocalDate.now());
        }

        PostModel postSalvo = postRepository.save(post);

        return postMapper.toResponse(postSalvo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll() {

        return postRepository.findAll()
                .stream()
                .map(postMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto findById(UUID id) {

        Optional<PostModel> optionalPost =
                postRepository.findById(id);

        if (optionalPost.isEmpty()) {

            throw new RegistroNaoEncontradoException(
                    "Post não encontrado com o ID: " + id
            );
        }

        return postMapper.toResponse(
                optionalPost.get()
        );
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(
            UUID id,
            PostRequestDto dto) {

        PostModel post = buscarPostPorId(id);

        post.setAutor(dto.autor());        
        post.setTitulo(dto.titulo());
        post.setTexto(dto.texto());

        // A data de criação original (post.getData()) é mantida inalterada no PUT

        PostModel postAtualizado = postRepository.save(post);

        return postMapper.toResponse(postAtualizado);
    }

    @Override
    @Transactional
    public void deletePost(UUID id) {

        PostModel post = buscarPostPorId(id);

        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    private PostModel buscarPostPorId(UUID id) {

        return postRepository.findById(id)
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(
                                "Post não encontrado com o ID: " + id
                        )
                );
    }
}