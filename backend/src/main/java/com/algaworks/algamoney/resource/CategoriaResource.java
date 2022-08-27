package com.algaworks.algamoney.resource;

import com.algaworks.algamoney.event.RecursoCriadoEvent;
import com.algaworks.algamoney.model.Categoria;
import com.algaworks.algamoney.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

  @Autowired
  private CategoriaRepository categoriaRepository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public List<Categoria> listar() {
    return categoriaRepository.findAll();
  }

  @PostMapping
  public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
    Categoria categoriaSalva = categoriaRepository.save(categoria);
    publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
    return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
    return this.categoriaRepository.findById(codigo)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }
}
