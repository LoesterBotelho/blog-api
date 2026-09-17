package com.api.blog_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.blog_api.dto.request.PostRequest;
import com.api.blog_api.dto.response.PostResponse;
import com.api.blog_api.mapper.PostMapper;
import com.api.blog_api.model.PostModel;
import com.api.blog_api.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostService postService;

    private UUID id;
    private PostModel post;
    private PostRequest request;
    private PostResponse response;

    @BeforeEach
    void setUp() {

        id = UUID.randomUUID();

        post = new PostModel(
                id,
                "Loester Botelho",
                LocalDate.of(2026, 9, 16),
                "Aprendendo Spring Boot",
                "Conteúdo sobre desenvolvimento de APIs REST."
        );

        request = new PostRequest(
                "Loester Botelho",
                LocalDate.of(2026, 9, 16),
                "Aprendendo Spring Boot",
                "Conteúdo sobre desenvolvimento de APIs REST."
        );

        response = new PostResponse(
                id,
                "Loester Botelho",
                LocalDate.of(2026, 9, 16),
                "Aprendendo Spring Boot",
                "Conteúdo sobre desenvolvimento de APIs REST."
        );
    }

    @Test
    void deveListarTodosOsPosts() {

        when(postRepository.findAll())
                .thenReturn(List.of(post));

        when(postMapper.toResponse(post))
                .thenReturn(response);

        List<PostResponse> resultado =
                postService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(response, resultado.get(0));

        verify(postRepository).findAll();
        verify(postMapper).toResponse(post);
    }

    @Test
    void deveObterPostPorId() {

        when(postRepository.findById(id))
                .thenReturn(Optional.of(post));

        when(postMapper.toResponse(post))
                .thenReturn(response);

        PostResponse resultado =
                postService.obterPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.id());
        assertEquals("Loester Botelho", resultado.autor());
        assertEquals("Aprendendo Spring Boot", resultado.titulo());

        verify(postRepository).findById(id);
        verify(postMapper).toResponse(post);
    }

    @Test
    void deveLancarExcecaoQuandoPostNaoForEncontrado() {

        when(postRepository.findById(id))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> postService.obterPorId(id)
                );

        assertEquals(
                "Post não encontrado. Id: " + id,
                exception.getMessage()
        );

        verify(postRepository).findById(id);
        verify(postMapper, never()).toResponse(any());
    }

    @Test
    void deveIncluirPost() {

        when(postMapper.toModel(request))
                .thenReturn(post);

        when(postRepository.save(post))
                .thenReturn(post);

        when(postMapper.toResponse(post))
                .thenReturn(response);

        PostResponse resultado =
                postService.incluir(request);

        assertNotNull(resultado);
        assertEquals(id, resultado.id());
        assertEquals("Loester Botelho", resultado.autor());
        assertEquals("Aprendendo Spring Boot", resultado.titulo());

        verify(postMapper).toModel(request);
        verify(postRepository).save(post);
        verify(postMapper).toResponse(post);
    }

    @Test
    void deveAtualizarPost() {

        when(postRepository.findById(id))
                .thenReturn(Optional.of(post));

        when(postRepository.save(post))
                .thenReturn(post);

        when(postMapper.toResponse(post))
                .thenReturn(response);

        PostResponse resultado =
                postService.atualizar(id, request);

        assertNotNull(resultado);
        assertEquals(id, resultado.id());

        assertEquals(request.autor(), post.getAutor());
        assertEquals(request.data(), post.getData());
        assertEquals(request.titulo(), post.getTitulo());
        assertEquals(request.texto(), post.getTexto());

        verify(postRepository).findById(id);
        verify(postRepository).save(post);
        verify(postMapper).toResponse(post);
    }

    @Test
    void deveLancarExcecaoAoAtualizarPostInexistente() {

        when(postRepository.findById(id))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> postService.atualizar(id, request)
                );

        assertEquals(
                "Post não encontrado. Id: " + id,
                exception.getMessage()
        );

        verify(postRepository).findById(id);
        verify(postRepository, never()).save(any());
    }

    @Test
    void deveDeletarPost() {

        when(postRepository.findById(id))
                .thenReturn(Optional.of(post));

        postService.deletar(id);

        verify(postRepository).findById(id);
        verify(postRepository).delete(post);
    }

    @Test
    void deveLancarExcecaoAoDeletarPostInexistente() {

        when(postRepository.findById(id))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> postService.deletar(id)
                );

        assertEquals(
                "Post não encontrado. Id: " + id,
                exception.getMessage()
        );

        verify(postRepository).findById(id);
        verify(postRepository, never()).delete(any());
    }
}
