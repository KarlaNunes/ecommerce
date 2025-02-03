package br.ifrn.edu.jeferson.ecommerce.controller.swagger;

import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoPatchDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.queryFilters.PedidoQueryFilter;
import br.ifrn.edu.jeferson.ecommerce.utils.CustomPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pedidos", description = "API de gerenciamento de pedidos")
public interface PedidoControllerInterface {
    @Operation(summary = "Criar um novo pedido")
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> salvarPedido(@RequestBody @Valid PedidoRequestDTO pedidoRequestDTO);

    @Operation(
            summary = "Listar pedidos",
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
    public ResponseEntity<CustomPage<PedidoResponseDTO>> listarPedidos(
            @Parameter(hidden = true) Pageable pageable,
            PedidoQueryFilter filter
    );

    @Operation(summary = "Buscar pedido por id")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable("id") Long id);

    @Operation(summary = "Buscar pedidos de um cliente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPedidoPorCliente(@PathVariable("clienteId") Long clienteId);

    @Operation(summary = "Editar pedido")
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> editarPedido(@PathVariable Long id,@RequestBody PedidoPatchDTO pedidoPatchDTO);

    @Operation(summary = "Excluir pedido")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPedido(@PathVariable Long id);
}
