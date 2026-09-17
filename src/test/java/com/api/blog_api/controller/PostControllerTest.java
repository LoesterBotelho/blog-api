package com.api.blog_api.controller;

import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.api.blog_api.dto.request.PostRequestDto;
import com.api.blog_api.dto.response.PostResponseDto;
import com.api.blog_api.service.PostServiceImpl;

@WebMvcTest(PostController.class)
class PostControllerTest {

    // ==========================================================
    // MOCK MVC
    // ==========================================================

    @Autowired
    private MockMvc mockMvc;

    // ==========================================================
    // MOCK DO SERVICE
    // ==========================================================

    @MockitoBean
    private PostServiceImpl postService;

    // ==========================================================
    // DADOS DOS TESTES
    // ==========================================================

    private UUID id;

    private PostResponseDto response;

    // ==========================================================
    // CONFIGURAÇÃO INICIAL
    // ==========================================================

    @BeforeEach
    void setUp() {

        id = UUID.randomUUID();

        new PostRequestDto(
                "Loester Botelho",
                LocalDate.of(2026, 9, 16),
                "Aprendendo Spring Boot",
                "Conteúdo sobre desenvolvimento de APIs REST."
        );

        response = new PostResponseDto(
                id,
                "Loester Botelho",
                LocalDate.of(2026, 9, 16),
                "Aprendendo Spring Boot",
                "Conteúdo sobre desenvolvimento de APIs REST."
        );
    }

    // ==========================================================
    // GET /posts
    // ==========================================================

    @Test
    void deveListarTodosOsPosts() throws Exception {

        when(postService.listarTodos())
                .thenReturn(List.of(response));

        mockMvc.perform(
                get("/posts")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id")
                        .value(id.toString()))
                .andExpect(jsonPath("$[0].autor")
                        .value("Loester Botelho"))
                .andExpect(jsonPath("$[0].data")
                        .value("2026-09-16"))
                .andExpect(jsonPath("$[0].titulo")
                        .value("Aprendendo Spring Boot"))
                .andExpect(jsonPath("$[0].texto")
                        .value("Conteúdo sobre desenvolvimento de APIs REST."));
    }

    // ==========================================================
    // GET /posts/{id}
    // ==========================================================

    @Test
    void deveObterPostPorId() throws Exception {

        when(postService.obterPorId(id))
                .thenReturn(response);

        mockMvc.perform(
                get("/posts/{id}", id)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(id.toString()))
                .andExpect(jsonPath("$.autor")
                        .value("Loester Botelho"))
                .andExpect(jsonPath("$.data")
                        .value("2026-09-16"))
                .andExpect(jsonPath("$.titulo")
                        .value("Aprendendo Spring Boot"))
                .andExpect(jsonPath("$.texto")
                        .value("Conteúdo sobre desenvolvimento de APIs REST."));
    }

    // ==========================================================
    // POST /posts
    // ==========================================================

    @Test
    void deveIncluirPost() throws Exception {

        when(postService.incluir(any(PostRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "Loester Botelho",
                                    "data": "2026-09-16",
                                    "titulo": "Aprendendo Spring Boot",
                                    "texto": "Conteúdo sobre desenvolvimento de APIs REST."
                                }
                                """)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id")
                        .value(id.toString()))
                .andExpect(jsonPath("$.autor")
                        .value("Loester Botelho"))
                .andExpect(jsonPath("$.data")
                        .value("2026-09-16"))
                .andExpect(jsonPath("$.titulo")
                        .value("Aprendendo Spring Boot"))
                .andExpect(jsonPath("$.texto")
                        .value("Conteúdo sobre desenvolvimento de APIs REST."));
    }

    // ==========================================================
    // PUT /posts/{id}
    // ==========================================================

    @Test
    void deveAtualizarPost() throws Exception {

        when(postService.atualizar(
                any(UUID.class),
                any(PostRequestDto.class)
        ))
                .thenReturn(response);

        mockMvc.perform(
                put("/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "Loester Botelho",
                                    "data": "2026-09-16",
                                    "titulo": "Aprendendo Spring Boot",
                                    "texto": "Conteúdo sobre desenvolvimento de APIs REST."
                                }
                                """)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(id.toString()))
                .andExpect(jsonPath("$.autor")
                        .value("Loester Botelho"))
                .andExpect(jsonPath("$.data")
                        .value("2026-09-16"))
                .andExpect(jsonPath("$.titulo")
                        .value("Aprendendo Spring Boot"))
                .andExpect(jsonPath("$.texto")
                        .value("Conteúdo sobre desenvolvimento de APIs REST."));
    }

    // ==========================================================
    // DELETE /posts/{id}
    // ==========================================================

    @Test
    void deveDeletarPost() throws Exception {

        mockMvc.perform(
                delete("/posts/{id}", id)
        )
                .andExpect(status().isNoContent());
    }

    // ==========================================================
    // VALIDAÇÃO - AUTOR VAZIO
    // ==========================================================

    @Test
    void deveRetornarBadRequestQuandoAutorEstiverVazio()
            throws Exception {

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "",
                                    "data": "2026-09-16",
                                    "titulo": "Aprendendo Spring Boot",
                                    "texto": "Conteúdo sobre desenvolvimento de APIs REST."
                                }
                                """)
        )
                .andExpect(status().isBadRequest());
    }

    // ==========================================================
    // VALIDAÇÃO - AUTOR MUITO CURTO
    // ==========================================================

    @Test
    void deveRetornarBadRequestQuandoAutorForMuitoCurto()
            throws Exception {

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "AB",
                                    "data": "2026-09-16",
                                    "titulo": "Aprendendo Spring Boot",
                                    "texto": "Conteúdo sobre desenvolvimento de APIs REST."
                                }
                                """)
        )
                .andExpect(status().isBadRequest());
    }

    // ==========================================================
    // VALIDAÇÃO - TÍTULO VAZIO
    // ==========================================================

    @Test
    void deveRetornarBadRequestQuandoTituloEstiverVazio()
            throws Exception {

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "Loester Botelho",
                                    "data": "2026-09-16",
                                    "titulo": "",
                                    "texto": "Conteúdo sobre desenvolvimento de APIs REST."
                                }
                                """)
        )
                .andExpect(status().isBadRequest());
    }

    // ==========================================================
    // VALIDAÇÃO - TÍTULO MUITO CURTO
    // ==========================================================

    @Test
    void deveRetornarBadRequestQuandoTituloForMuitoCurto()
            throws Exception {

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "Loester Botelho",
                                    "data": "2026-09-16",
                                    "titulo": "AB",
                                    "texto": "Conteúdo sobre desenvolvimento de APIs REST."
                                }
                                """)
        )
                .andExpect(status().isBadRequest());
    }

    // ==========================================================
    // VALIDAÇÃO - DATA NULA
    // ==========================================================

    @Test
    void deveRetornarBadRequestQuandoDataForNula()
            throws Exception {

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "Loester Botelho",
                                    "data": null,
                                    "titulo": "Aprendendo Spring Boot",
                                    "texto": "Conteúdo sobre desenvolvimento de APIs REST."
                                }
                                """)
        )
                .andExpect(status().isBadRequest());
    }

    // ==========================================================
    // VALIDAÇÃO - DATA FUTURA
    // ==========================================================

    @Test
    void deveRetornarBadRequestQuandoDataForFutura()
            throws Exception {

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "Loester Botelho",
                                    "data": "2099-01-01",
                                    "titulo": "Aprendendo Spring Boot",
                                    "texto": "Conteúdo sobre desenvolvimento de APIs REST."
                                }
                                """)
        )
                .andExpect(status().isBadRequest());
    }

    // ==========================================================
    // VALIDAÇÃO - TEXTO VAZIO
    // ==========================================================

    @Test
    void deveRetornarBadRequestQuandoTextoEstiverVazio()
            throws Exception {

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "Loester Botelho",
                                    "data": "2026-09-16",
                                    "titulo": "Aprendendo Spring Boot",
                                    "texto": ""
                                }
                                """)
        )
                .andExpect(status().isBadRequest());
    }

    // ==========================================================
    // VALIDAÇÃO - TEXTO MUITO CURTO
    // ==========================================================

    @Test
    void deveRetornarBadRequestQuandoTextoForMuitoCurto()
            throws Exception {

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "autor": "Loester Botelho",
                                    "data": "2026-09-16",
                                    "titulo": "Aprendendo Spring Boot",
                                    "texto": "123456789"
                                }
                                """)
        )
                .andExpect(status().isBadRequest());
    }
}