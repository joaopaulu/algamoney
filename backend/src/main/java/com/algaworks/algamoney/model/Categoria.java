package com.algaworks.algamoney.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categoria")
public class Categoria {

  private static final int minSize = 3;
  private static final int maxSize = 20;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long codigo;

  @NotNull
  @Size(min = minSize, max = maxSize)
  private String nome;

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Categoria)) {
      return false;
    }
    Categoria categoria = (Categoria) o;
    return Objects.equals(codigo, categoria.codigo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigo);
  }

}
