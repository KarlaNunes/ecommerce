package br.ifrn.edu.jeferson.ecommerce.specifications;

import br.ifrn.edu.jeferson.ecommerce.domain.Produto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class ProdutoSpec {
    public static Specification<Produto> nomeContains(String nome) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(nome)) {
                return null;
            }
            return builder.like(root.get("nome"), "%" + nome + "%");
        };
    }

    public static Specification<Produto> estoqueGreaterThanOrEqualsTo(Integer estoque) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(estoque)) {
                return null;
            }
            return builder.greaterThanOrEqualTo(root.get("estoque"), estoque);
        };
    };

    public static Specification<Produto> categoriaEqualsTo(String categoria) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(categoria)) {
                return null;
            }

            return builder.equal(root.join("categorias").join("categoria").get("nome"), categoria);
        };
    }
}
