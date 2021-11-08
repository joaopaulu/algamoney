package com.algaworks.algamoney.repository;

import com.algaworks.algamoney.repository.lancamento.LancamentoRepositoryQuery;
import com.algaworks.algamoney.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
