package br.ifrn.edu.jeferson.ecommerce.controller;

import br.ifrn.edu.jeferson.ecommerce.controller.swagger.CategoriaControllerInterface;
import br.ifrn.edu.jeferson.ecommerce.domain.Categoria;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.exception.BusinessException;
import br.ifrn.edu.jeferson.ecommerce.queryFilters.CategoriaQueryFilter;
import br.ifrn.edu.jeferson.ecommerce.repository.CategoriaRepository;
import br.ifrn.edu.jeferson.ecommerce.service.CategoriaService;
import br.ifrn.edu.jeferson.ecommerce.utils.CustomPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/categorias")
public class CategoriaController implements CategoriaControllerInterface {
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> salvar(@RequestBody CategoriaRequestDTO categoriaDto) {
        Optional<Categoria> categoria = categoriaRepository.findByNome(categoriaDto.getNome());

        if (categoria.isPresent()) {
            throw new BusinessException("Nome deve ser único");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.salvar(categoriaDto));
    }


    @GetMapping
    public ResponseEntity<CustomPage<CategoriaResponseDTO>> listar(
           Pageable pageable,
           CategoriaQueryFilter filter
    ) {
        Page<CategoriaResponseDTO> response = categoriaService.lista(pageable, filter.toSpecification());
        var customPageResponse = new CustomPage<>(response);
        return ResponseEntity.ok(customPageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id, @RequestBody CategoriaRequestDTO categoriaDto) {
        return ResponseEntity.ok(categoriaService.atualizar(id, categoriaDto));
    }

    @PostMapping("/{categoriaId}/produtos/{produtoId}")
    public ResponseEntity<CategoriaResponseDTO> associarProduto(@PathVariable Long categoriaId, @PathVariable Long produtoId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.associarProduto(produtoId, categoriaId));
    }

    @DeleteMapping("/{categoriaId}/produtos/{produtoId}")
    public ResponseEntity<CategoriaResponseDTO> removerProdutoDeCategoria(@PathVariable Long categoriaId, @PathVariable Long produtoId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.removerProduto(produtoId, categoriaId));
    }
}
