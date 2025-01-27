package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import lombok.Data;

@Data
public class ItemPedidoRequestDTO {
    private Integer quantidade;
    private Long produtoId;
}
