package com.api.blog_api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.blog_api.model.CommentModel;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, UUID> {
	List<CommentModel> findByPostId(UUID postId);
}