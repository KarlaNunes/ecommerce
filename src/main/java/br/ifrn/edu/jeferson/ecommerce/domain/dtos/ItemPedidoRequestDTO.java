package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemPedidoRequestDTO {
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private Long produtoId;
}
