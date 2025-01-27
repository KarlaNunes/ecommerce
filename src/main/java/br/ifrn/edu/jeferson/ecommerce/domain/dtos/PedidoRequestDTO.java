package br.ifrn.edu.jeferson.ecommerce.domain.dtos;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoRequestDTO {
    private Long clienteId;
    @Size(min = 1, message = "Quantidade de itens deve ser maior que zero")
    private List<ItemPedidoRequestDTO> itens = new ArrayList<>();
}
