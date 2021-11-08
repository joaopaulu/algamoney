package com.algaworks.algamoney.service;

import com.algaworks.algamoney.model.Lancamento;
import com.algaworks.algamoney.model.Pessoa;
import com.algaworks.algamoney.repository.LancamentoRepository;
import com.algaworks.algamoney.repository.PessoaRepository;
import com.algaworks.algamoney.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    LancamentoRepository lancamentoRepository;

    public Lancamento salvar(Lancamento lancamento){
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
        if(pessoaOpt.isEmpty() || pessoaOpt.get().isInativo()) {
            throw new PessoaInexistenteOuInativaException();
        }
        return lancamentoRepository.save(lancamento);
    }

    public Lancamento atualizar(Long codigo, Lancamento lancamento){
        final Lancamento lancamentoSalva = buscarLancamentoPeloCodigo(codigo);
        BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo");

        return this.lancamentoRepository.save(lancamentoSalva);
    }

    private Lancamento buscarLancamentoPeloCodigo(Long codigo) {
        return this.lancamentoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
}
