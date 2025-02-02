package br.ifrn.edu.jeferson.ecommerce.specifications;

import br.ifrn.edu.jeferson.ecommerce.domain.Categoria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class CategoriaSpec {

    public static Specification<Categoria> nomeContains(String nome) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(nome)) {
                return null;
            }

            return builder.like(root.get("nome"), "%" + nome + "%");
        };
    }
}
