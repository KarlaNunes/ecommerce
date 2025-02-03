package br.ifrn.edu.jeferson.ecommerce.controller;
import br.ifrn.edu.jeferson.ecommerce.controller.swagger.ProdutoControllerInterface;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoPatchDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.queryFilters.ProdutoQueryFilter;
import br.ifrn.edu.jeferson.ecommerce.service.ProdutoService;
import br.ifrn.edu.jeferson.ecommerce.utils.CustomPage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController implements ProdutoControllerInterface {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(produtoRequestDTO));
    }

    @GetMapping
    public ResponseEntity<CustomPage<ProdutoResponseDTO>> listarProdutos(
           Pageable pageable,
           ProdutoQueryFilter filter
    ) {
        Page<ProdutoResponseDTO> response = produtoService.listar(pageable, filter.buildSpecification());
        var customPageResponse = new CustomPage<>(response);
        return ResponseEntity.status(HttpStatus.OK).body(customPageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.atualizar(id, produtoRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        produtoService.remover(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<ProdutoResponseDTO> atualizarEstoque(@PathVariable Long id, @RequestBody ProdutoPatchDTO produtoPatchDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.atualizarEstoque(id, produtoPatchDTO));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarCategorias(@PathVariable Long categoriaId) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listarPorCategoria(categoriaId));
    }
}
