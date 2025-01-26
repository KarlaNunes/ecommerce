package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import br.ifrn.edu.jeferson.ecommerce.domain.Cliente;
import br.ifrn.edu.jeferson.ecommerce.domain.ItemPedido;
import br.ifrn.edu.jeferson.ecommerce.domain.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoRequestDTO {
    private LocalDateTime dataPedido;
    private BigDecimal valorTotal;
    private String statusPedido;
    private Long clienteId;
    private List<ItemPedidoRequestDTO> itens = new ArrayList<>();
}
