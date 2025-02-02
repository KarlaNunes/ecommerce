package br.ifrn.edu.jeferson.ecommerce.specifications;

import br.ifrn.edu.jeferson.ecommerce.domain.Pedido;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PedidoSpec {
    public static Specification<Pedido> dataContains(String data){
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(data)){
                return null;
            }

            LocalDate localDate = LocalDate.parse(data);
            LocalDateTime startOfDay = localDate.atStartOfDay();
            LocalDateTime endOfDay = localDate.atTime(23, 59, 59, 999999);

            return builder.and(
                    builder.greaterThanOrEqualTo(root.get("dataPedido"), startOfDay),
                    builder.lessThanOrEqualTo(root.get("dataPedido"), endOfDay)
            );
        };
    }
}
