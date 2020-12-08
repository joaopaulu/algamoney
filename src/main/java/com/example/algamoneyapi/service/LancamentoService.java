package com.example.algamoneyapi.service;

import com.example.algamoneyapi.model.Lancamento;
import com.example.algamoneyapi.repository.LancamentoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Lancamento atualizar(Long codigo, Lancamento lancamento){
        final Lancamento lancamentoSalva = buscarLancamentoPeloCodigo(codigo);
        BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo");

        return this.lancamentoRepository.save(lancamentoSalva);
    }

    private Lancamento buscarLancamentoPeloCodigo(Long codigo) {
        return this.lancamentoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
}
