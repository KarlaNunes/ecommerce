package br.ifrn.edu.jeferson.ecommerce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    @PositiveOrZero(message = "Preço não pode ser negativo")
    private BigDecimal preco;
    @PositiveOrZero(message = "Estoque não pode ser negativo")
    private Integer estoque;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoCategoria> categorias = new ArrayList<>();

}
