package com.example.algamoneyapi.resource;

import com.example.algamoneyapi.event.RecursoCriadoEvent;
import com.example.algamoneyapi.model.Pessoa;
import com.example.algamoneyapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public List<Pessoa> listar() {
    return pessoaRepository.findAll();
  }

  @PostMapping
  public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
    Pessoa pessoaSalva = pessoaRepository.save(pessoa);
    publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
    return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<Pessoa> pessoa = this.pessoaRepository.findById(codigo);
    return pessoa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

}