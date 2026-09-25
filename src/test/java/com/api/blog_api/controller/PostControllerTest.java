package com.api.blog_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.api.blog_api.dto.request.PostRequestDto;
import com.api.blog_api.dto.response.PostResponseDto;
import com.api.blog_api.service.PostService;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    private UUID id;
    private PostResponseDto response;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();

        response = new PostResponseDto(
                id,
                "Loester Botelho",
                LocalDate.of(2026, 9, 16),
                "Aprendendo Spring Boot",
                "Conteúdo sobre desenvolvimento de APIs REST.",
                List.of()
        );
    }

    @Test
    void deveListarTodosOsPosts() throws Exception {
        when(postService.findAll()).thenReturn(List.of(response));

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].autor").value("Loester Botelho"))
                .andExpect(jsonPath("$[0].data").value("2026-09-16"))
                .andExpect(jsonPath("$[0].titulo").value("Aprendendo Spring Boot"))
                .andExpect(jsonPath("$[0].texto").value("Conteúdo sobre desenvolvimento de APIs REST."));
    }

    @Test
    void deveObterPostPorId() throws Exception {
        when(postService.findById(id)).thenReturn(response);

        mockMvc.perform(get("/posts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.autor").value("Loester Botelho"))
                .andExpect(jsonPath("$.data").value("2026-09-16"))
                .andExpect(jsonPath("$.titulo").value("Aprendendo Spring Boot"))
                .andExpect(jsonPath("$.texto").value("Conteúdo sobre desenvolvimento de APIs REST."));
    }

    @Test
    void deveIncluirPost() throws Exception {
        when(postService.createPost(any(PostRequestDto.class))).thenReturn(response);

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "Loester Botelho",
                                    "titulo": "Aprendendo Spring Boot",
                                    "texto": "Conteúdo sobre desenvolvimento de APIs REST."
                                }
                                """)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.autor").value("Loester Botelho"))
                .andExpect(jsonPath("$.data").value("2026-09-16"))
                .andExpect(jsonPath("$.titulo").value("Aprendendo Spring Boot"))
                .andExpect(jsonPath("$.texto").value("Conteúdo sobre desenvolvimento de APIs REST."));
    }

    @Test
    void deveAtualizarPost() throws Exception {
        when(postService.updatePost(eq(id), any(PostRequestDto.class))).thenReturn(response);

        mockMvc.perform(
                put("/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "Loester Botelho",
                                    "titulo": "Aprendendo Spring Boot",
                                    "texto": "Conteúdo sobre desenvolvimento de APIs REST."
                                }
                                """)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.autor").value("Loester Botelho"))
                .andExpect(jsonPath("$.data").value("2026-09-16"))
                .andExpect(jsonPath("$.titulo").value("Aprendendo Spring Boot"))
                .andExpect(jsonPath("$.texto").value("Conteúdo sobre desenvolvimento de APIs REST."));
    }

    @Test
    void deveDeletarPost() throws Exception {
        doNothing().when(postService).deletePost(id);

        mockMvc.perform(delete("/posts/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarBadRequestQuandoAutorEstiverVazio() throws Exception {
        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "",
                                    "titulo": "Aprendendo Spring Boot",
                                    "texto": "Conteúdo sobre desenvolvimento de APIs REST."
                                }
                                """)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoAutorForMuitoCurto() throws Exception {
        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "AB",
                                    "titulo": "Aprendendo Spring Boot",
                                    "texto": "Conteúdo sobre desenvolvimento de APIs REST."
                                }
                                """)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoTituloEstiverVazio() throws Exception {
        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "Loester Botelho",
                                    "titulo": "",
                                    "texto": "Conteúdo sobre desenvolvimento de APIs REST."
                                }
                                """)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoTituloForMuitoCurto() throws Exception {
        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "Loester Botelho",
                                    "titulo": "AB",
                                    "texto": "Conteúdo sobre desenvolvimento de APIs REST."
                                }
                                """)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoTextoEstiverVazio() throws Exception {
        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "Loester Botelho",
                                    "titulo": "Aprendendo Spring Boot",
                                    "texto": ""
                                }
                                """)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoTextoForMuitoCurto() throws Exception {
        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "Loester Botelho",
                                    "titulo": "Aprendendo Spring Boot",
                                    "texto": "123456789"
                                }
                                """)
        )
                .andExpect(status().isBadRequest());
    }
}