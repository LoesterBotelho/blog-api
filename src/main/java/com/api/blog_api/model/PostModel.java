package com.api.blog_api.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_post")
public class PostModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private String autor;

	@Column(nullable = false)
	private LocalDate data;

	@Column(nullable = false, length = 100)
	private String titulo;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String texto;

	public PostModel() {

	}
	
	public PostModel(String autor, LocalDate data, String titulo, String texto) {
		super();
		this.autor = autor;
		this.data = data;
		this.titulo = titulo;
		this.texto = texto;
	}

	public PostModel(UUID id, String autor, LocalDate data, String titulo, String texto) {
		this.id = id;
		this.autor = autor;
		this.data = data;
		this.titulo = titulo;
		this.texto = texto;
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

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Override
	public String toString() {
		return "PostModel [id=" + this.getId() + 
				", autor=" + this.getAutor() + 
				", data=" + this.getData() + 
				", Titulo=" + this.getTitulo() + 
				", texto=" + this.getTexto()
				+ "]";
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
		PostModel other = (PostModel) obj;
		return Objects.equals(id, other.id);
	}

}
