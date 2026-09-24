package com.api.blog_api.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_comment")
public class CommentModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private String autor;

	@Column(nullable = false)
	private LocalDate data;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String texto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private PostModel post;

	public CommentModel() {

	}

	public CommentModel(String autor, String texto, PostModel post) {
		this.autor = autor;
		this.data = LocalDate.now();
		this.texto = texto;
		this.post = post;
	}

	public CommentModel(UUID id, String autor, String texto, PostModel post) {
		this.id = id;
		this.autor = autor;
		this.data = LocalDate.now();
		this.texto = texto;
		this.post = post;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public PostModel getPost() {
		return post;
	}

	public void setPost(PostModel post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "CommentModel [id=" + this.getId() + 
				", autor=" + this.getAutor() + 
				", data=" + this.getData() + 
				", texto=" + this.getTexto() + 
				"]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentModel other = (CommentModel) obj;
		return Objects.equals(id, other.id);
	}

}