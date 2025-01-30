package br.ifrn.edu.jeferson.ecommerce.controller;

import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoPatchDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "API de gerenciamento de pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Criar um novo pedido")
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> salvarPedido(@RequestBody @Valid PedidoRequestDTO pedidoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.salvar(pedidoRequestDTO));
    }

    @Operation(summary = "Listar pedidos")
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.listar());
    }

    @Operation(summary = "Buscar pedido por id")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.buscarPorId(id));
    }

    @Operation(summary = "Buscar pedidos de um cliente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPedidoPorCliente(@PathVariable("clienteId") Long clienteId) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.buscarPorCliente(clienteId));
    }

    @Operation(summary = "Editar pedido")
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
