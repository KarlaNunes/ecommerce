package br.ifrn.edu.jeferson.ecommerce.controller.swagger;

import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.queryFilters.CategoriaQueryFilter;
import br.ifrn.edu.jeferson.ecommerce.utils.CustomPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Categorias", description = "API de gerenciamento de categorias dos Produtos")
public interface CategoriaControllerInterface {

    @Operation(summary = "Criar uma nova categoria")
    @PostMapping
    ResponseEntity<CategoriaResponseDTO> salvar(@RequestBody CategoriaRequestDTO categoriaDto);

    @Operation(
            summary = "Listar categorias",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Número da página",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "integer", defaultValue = "0")
                    ),
                    @Parameter(
                            name = "size",
                            description = "Número de itens por página",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "integer", defaultValue = "20")
                    )
            }
    )
    @GetMapping
    ResponseEntity<CustomPage<CategoriaResponseDTO>> listar(
            @Parameter(hidden = true) Pageable pageable,
            CategoriaQueryFilter filter
    );

    @Operation(summary = "Listar categoria por id")
    @GetMapping("/{id}")
    ResponseEntity<CategoriaResponseDTO> listarPorId(@PathVariable Long id);

    @Operation(summary = "Deletar uma nova categoria")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletar(@PathVariable Long id);

    @Operation(summary = "Atualizar uma nova categoria")
    @PutMapping("/{id}")
    ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id, @RequestBody CategoriaRequestDTO categoriaDto);

    @Operation(summary = "Associar produto à categoria")
    @PostMapping("/{categoriaId}/produtos/{produtoId}")
    ResponseEntity<CategoriaResponseDTO> associarProduto(@PathVariable Long categoriaId, @PathVariable Long produtoId);

    @Operation(summary = "Remover produto de categoria")
    @DeleteMapping("/{categoriaId}/produtos/{produtoId}")
    ResponseEntity<CategoriaResponseDTO> removerProdutoDeCategoria(@PathVariable Long categoriaId, @PathVariable Long produtoId);
}