package br.ifrn.edu.jeferson.ecommerce.controller;

import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoPatchDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.queryFilters.PedidoQueryFilter;
import br.ifrn.edu.jeferson.ecommerce.service.PedidoService;
import br.ifrn.edu.jeferson.ecommerce.utils.CustomPage;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> salvarPedido(@RequestBody @Valid PedidoRequestDTO pedidoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.salvar(pedidoRequestDTO));
    }

    @GetMapping
    public ResponseEntity<CustomPage<PedidoResponseDTO>> listarPedidos(
           Pageable pageable,
           PedidoQueryFilter filter
    ) {
        Page<PedidoResponseDTO> response = pedidoService.listar(pageable, filter.buildSpecification());
        var customPageResponse = new CustomPage<>(response);
        return ResponseEntity.status(HttpStatus.OK).body(customPageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.buscarPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPedidoPorCliente(@PathVariable("clienteId") Long clienteId) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.buscarPorCliente(clienteId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> editarPedido(@PathVariable Long id,@RequestBody PedidoPatchDTO pedidoPatchDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.atualizarStatus(id,pedidoPatchDTO));
    }

    @Operation(summary = "Excluir pedido")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPedido(@PathVariable Long id) {
        pedidoService.remover(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
