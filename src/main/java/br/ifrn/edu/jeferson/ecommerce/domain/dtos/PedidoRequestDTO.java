package br.ifrn.edu.jeferson.ecommerce.domain.dtos;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoRequestDTO {
    private Long clienteId;
    private List<ItemPedidoRequestDTO> itens = new ArrayList<>();
}
