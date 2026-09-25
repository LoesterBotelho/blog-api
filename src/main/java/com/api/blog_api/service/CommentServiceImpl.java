package com.api.blog_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.blog_api.dto.request.CommentRequestDto;
import com.api.blog_api.dto.response.CommentResponseDto;
import com.api.blog_api.exception.RegistroNaoEncontradoException;
import com.api.blog_api.mapper.CommentMapper;
import com.api.blog_api.model.CommentModel;
import com.api.blog_api.model.PostModel;
import com.api.blog_api.repository.CommentRepository;
import com.api.blog_api.repository.PostRepository;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(
            CommentRepository commentRepository,
            PostRepository postRepository,
            CommentMapper commentMapper) {

        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional
    public CommentResponseDto createComment(UUID postId, CommentRequestDto dto) {

        PostModel post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(
                                "Post não encontrado com o ID: " + postId
                        )
                );

        CommentModel comment = commentMapper.toModel(dto);
        comment.setPost(post);

        // Garante que a data atual seja definida antes de salvar caso o mapper não a preencha
        if (comment.getData() == null) {
            comment.setData(LocalDate.now());
        }

        CommentModel commentSalvo = commentRepository.save(comment);

        return commentMapper.toResponse(commentSalvo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll() {

        return commentRepository.findAll()
                .stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findByPostId(UUID postId) {

        if (!postRepository.existsById(postId)) {
            throw new RegistroNaoEncontradoException(
                    "Post não encontrado com o ID: " + postId
            );
        }

        return commentRepository.findByPostId(postId)
                .stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponseDto findById(UUID id) {

        Optional<CommentModel> optionalComment =
                commentRepository.findById(id);

        if (optionalComment.isEmpty()) {

            throw new RegistroNaoEncontradoException(
                    "Comentário não encontrado com o ID: " + id
            );
        }

        return commentMapper.toResponse(
                optionalComment.get()
        );
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(
            UUID id,
            CommentRequestDto dto) {

        CommentModel comment = buscarComentarioPorId(id);

        comment.setAutor(dto.autor());
        comment.setTexto(dto.texto());

        CommentModel commentAtualizado = commentRepository.save(comment);

        return commentMapper.toResponse(commentAtualizado);
    }

    @Override
    @Transactional
    public void deleteComment(UUID id) {

        CommentModel comment = buscarComentarioPorId(id);

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    private CommentModel buscarComentarioPorId(UUID id) {

        return commentRepository.findById(id)
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(
                                "Comentário não encontrado com o ID: " + id
                        )
                );
    }
}