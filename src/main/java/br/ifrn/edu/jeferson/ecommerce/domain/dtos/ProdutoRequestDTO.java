package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para requisição de produto")
public class ProdutoRequestDTO {
    @Schema(description = "Nome do produto", example = "Notebook gamer")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Produto novo")
    private String descricao;

    @Schema(description = "Preço do produto", example = "100.00")
    @PositiveOrZero(message = "Preço não pode ser negativo")
    private BigDecimal preco;

    @Schema(description = "Estoque do produto", example = "1")
    @PositiveOrZero(message = "Estoque não pode ser negativo")
    private Integer estoque;
}
