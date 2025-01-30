package br.ifrn.edu.jeferson.ecommerce.controller;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoPatchDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "API de gerenciamento de produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Criar um novo produto")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(produtoRequestDTO));
    }

    @Operation(summary = "Listar produtos")
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listar());
    }

    @Operation(summary = "Buscar produto pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.buscarPorId(id));
    }

    @Operation(summary = "Atualizar produto")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.atualizar(id, produtoRequestDTO));
    }

    @Operation(summary = "Excluir produto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        produtoService.remover(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualizar estoque")
    @PatchMapping("/{id}/estoque")
    public ResponseEntity<ProdutoResponseDTO> atualizarEstoque(@PathVariable Long id, @RequestBody ProdutoPatchDTO produtoPatchDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.atualizarEstoque(id, produtoPatchDTO));
    }

    @Operation(summary = "Listar produtos por categoria")
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarCategorias(@PathVariable Long categoriaId) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listarPorCategoria(categoriaId));
    }
}
