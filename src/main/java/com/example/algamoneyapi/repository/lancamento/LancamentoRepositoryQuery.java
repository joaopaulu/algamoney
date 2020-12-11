package com.example.algamoneyapi.repository.lancamento;

import java.util.List;

import com.example.algamoneyapi.model.Lancamento;
import com.example.algamoneyapi.repository.filter.LancamentoFilter;


public interface LancamentoRepositoryQuery {
    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
