package com.api.blog_api.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.blog_api.model.PostModel;
import com.api.blog_api.repository.PostRepository;

@Component
public class DataUtil {

    @Autowired
    private PostRepository postRepository;

    //@PostConstruct
    public void savePosts() {

        List<PostModel> postList = new ArrayList<>();

        PostModel post1 = new PostModel();

        post1.setAutor("Loester Botelho");
        post1.setData(LocalDate.now());
        post1.setTitulo("Docker");

        post1.setTexto(
                "O Docker é uma plataforma utilizada para criar, executar e distribuir "
                + "aplicações em ambientes isolados chamados containers. "
                + "Com o Docker, é possível empacotar a aplicação junto com suas dependências, "
                + "facilitando a configuração do ambiente de desenvolvimento e evitando problemas "
                + "causados por diferenças entre as máquinas. "
                + "Em projetos Java com Spring Boot, o Docker pode ser utilizado para executar "
                + "a aplicação e também serviços externos, como bancos de dados MariaDB, "
                + "MySQL ou PostgreSQL."
        );

        PostModel post2 = new PostModel();

        post2.setAutor("Loester Botelho");
        post2.setData(LocalDate.now());
        post2.setTitulo("API REST");

        post2.setTexto(
                "Uma API REST permite que diferentes aplicações se comuniquem através da internet "
                + "utilizando o protocolo HTTP. "
                + "Em uma aplicação Spring Boot, podemos criar endpoints para realizar operações "
                + "como cadastrar, consultar, atualizar e excluir informações. "
                + "Os principais métodos HTTP utilizados em uma API REST são GET para consultas, "
                + "POST para criação, PUT ou PATCH para alterações e DELETE para exclusão. "
                + "Normalmente, os dados são enviados e recebidos no formato JSON, "
                + "facilitando a integração entre o backend e aplicações frontend."
        );

        postList.add(post1);
        postList.add(post2);

        for (PostModel post : postList) {

            PostModel postSaved = postRepository.save(post);

            System.out.println("Post salvo com sucesso: " + postSaved.getId());
        }
    }
}
