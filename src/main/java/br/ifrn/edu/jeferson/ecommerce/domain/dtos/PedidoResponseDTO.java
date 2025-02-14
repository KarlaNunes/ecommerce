package br.ifrn.edu.jeferson.ecommerce.domain.dtos;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoResponseDTO {
    private Long id;
    private LocalDateTime dataPedido;
    private BigDecimal valorTotal;
    private String statusPedido;
    private ClienteResponseDTO cliente;
    private List<ItemPedidoResponseDTO> itens = new ArrayList<>();
}
