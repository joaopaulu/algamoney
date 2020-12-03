package com.example.algamoneyapi.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.algamoneyapi.model.Categoria;
import com.example.algamoneyapi.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

  @Autowired
  private CategoriaRepository categoriaRepository;

  @GetMapping
  public List<Categoria> listar() {
    return categoriaRepository.findAll();
  }

  @PostMapping
  public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
    Categoria categoriaSalva = categoriaRepository.save(categoria);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
        .buildAndExpand(categoriaSalva.getCodigo()).toUri();
    response.setHeader("Location", uri.toASCIIString());

    return ResponseEntity.created(uri).body(categoriaSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<Categoria> categoria = this.categoriaRepository.findById(codigo);
    return categoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
