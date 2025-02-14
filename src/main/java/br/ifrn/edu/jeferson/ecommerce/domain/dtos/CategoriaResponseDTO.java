package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import br.ifrn.edu.jeferson.ecommerce.domain.Produto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para resposta de categoria")
public class CategoriaResponseDTO {
    @Schema(description = "ID da categoria", example = "1")
    private Long id;

    @Schema(description = "Nome da categoria", example = "Eletrônicos")
    private String nome;

    @Schema(description = "Descrição da categoria", example = "Produtos eletrônicos em geral")
    private String descricao;

    @Schema(description = "Produtos da categoria")
    private List<ProdutoResponseDTO> produtos;
}
