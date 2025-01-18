package br.ifrn.edu.jeferson.ecommerce.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoCategoriaId implements Serializable {
    private Long produtoId;
    private Long categoriaId;
}
