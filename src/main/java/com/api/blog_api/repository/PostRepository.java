package com.api.blog_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.blog_api.model.PostModel;

@Repository
public interface PostRepository extends JpaRepository<PostModel, UUID> {

}
