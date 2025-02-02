package br.ifrn.edu.jeferson.ecommerce.queryFilters;

import br.ifrn.edu.jeferson.ecommerce.domain.Pedido;
import static br.ifrn.edu.jeferson.ecommerce.specifications.PedidoSpec.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class PedidoQueryFilter {
    private String data;

    public Specification<Pedido> buildSpecification() {
        return dataContains(data);
    }
}
