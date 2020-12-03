package com.example.algamoneyapi.resource;

import com.example.algamoneyapi.model.Categoria;
import com.example.algamoneyapi.model.Pessoa;
import com.example.algamoneyapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

  @Autowired
  private PessoaRepository pessoaRepository;

  @GetMapping
  public List<Pessoa> listar() {
    return pessoaRepository.findAll();
  }

  @PostMapping
  public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
    Pessoa pessoaSalva = pessoaRepository.save(pessoa);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
            .buildAndExpand(pessoaSalva.getCodigo()).toUri();
    response.setHeader("Location", uri.toASCIIString());

    return ResponseEntity.created(uri).body(pessoaSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<Pessoa> pessoa = this.pessoaRepository.findById(codigo);
    return pessoa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

}