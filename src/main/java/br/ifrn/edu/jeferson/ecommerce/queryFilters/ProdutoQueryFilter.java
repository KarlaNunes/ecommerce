package br.ifrn.edu.jeferson.ecommerce.queryFilters;

import br.ifrn.edu.jeferson.ecommerce.domain.Produto;
import static br.ifrn.edu.jeferson.ecommerce.specifications.ProdutoSpec.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ProdutoQueryFilter {
    private String nome;
    private Integer estoque;
    private String nomeCategoria;

    public Specification<Produto> buildSpecification() {
        return nomeContains(nome)
                .and(estoqueGreaterThanOrEqualsTo(estoque))
                .and(categoriaEqualsTo(nomeCategoria));
    }
}
