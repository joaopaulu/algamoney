package com.algaworks.algamoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algamoney.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
