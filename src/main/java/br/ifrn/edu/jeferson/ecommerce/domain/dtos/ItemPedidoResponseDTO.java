package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemPedidoResponseDTO {
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal valorUnitario;
}
