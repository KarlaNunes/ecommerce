package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProdutoPatchDTO {
    @PositiveOrZero(message = "Estoque não pode ser negativo")
    private Integer estoque;
}
