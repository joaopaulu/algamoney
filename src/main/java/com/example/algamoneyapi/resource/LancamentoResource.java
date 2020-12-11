package com.example.algamoneyapi.resource;

import com.example.algamoneyapi.event.RecursoCriadoEvent;
import com.example.algamoneyapi.exceptionhandler.AlgamoneyExceptionHandler;
import com.example.algamoneyapi.model.Lancamento;
import com.example.algamoneyapi.repository.filter.LancamentoFilter;
import com.example.algamoneyapi.repository.LancamentoRepository;
import com.example.algamoneyapi.service.LancamentoService;
import com.example.algamoneyapi.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

  @Autowired
  private LancamentoRepository lancamentoRepository;

  @Autowired
  private LancamentoService lancamentoService;

  @Autowired
  private ApplicationEventPublisher publisher;

  @Autowired
  private MessageSource messageSource;

  @GetMapping
  public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter) {
    return lancamentoRepository.filtrar(lancamentoFilter);
  }

  @PostMapping
  public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
    Lancamento lancamentoSalva = lancamentoService.salvar(lancamento);
    publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalva.getCodigo()));
    return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<Lancamento> lancamento = this.lancamentoRepository.findById(codigo);
    return lancamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    lancamentoRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo, @Valid @RequestBody Lancamento lancamento){
    Lancamento lancamentoSalva = lancamentoService.atualizar(codigo, lancamento);
    return ResponseEntity.ok(lancamentoSalva);
  }

  @ExceptionHandler({PessoaInexistenteOuInativaException.class})
  public ResponseEntity<Object> handlePessoaInexistenteouInativaException(PessoaInexistenteOuInativaException ex){
    String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
    String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
    List<AlgamoneyExceptionHandler.Erro> erros = Collections.singletonList(new AlgamoneyExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
    return ResponseEntity.badRequest().body(erros);
  }

}