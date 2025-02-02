package br.ifrn.edu.jeferson.ecommerce.queryFilters;

import br.ifrn.edu.jeferson.ecommerce.domain.Categoria;
import static br.ifrn.edu.jeferson.ecommerce.specifications.CategoriaSpec.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class CategoriaQueryFilter {
    private String nome;

    public Specification<Categoria> toSpecification() {
        return nomeContains(nome);
    }
}
