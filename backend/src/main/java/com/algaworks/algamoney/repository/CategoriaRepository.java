package com.algaworks.algamoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algamoney.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}