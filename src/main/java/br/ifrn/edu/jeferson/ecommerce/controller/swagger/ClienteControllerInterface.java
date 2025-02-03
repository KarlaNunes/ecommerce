package br.ifrn.edu.jeferson.ecommerce.controller.swagger;

import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.service.ClienteService;
import br.ifrn.edu.jeferson.ecommerce.utils.CustomPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Clientes", description = "API de gerenciamento de clientes")
public interface ClienteControllerInterface {

    @Operation(summary = "Criar um novo cliente")
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> salvarCliente(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO);

    @Operation(
            summary = "Listar clientes",
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

    public ResponseEntity<CustomPage<ClienteResponseDTO>> listarClientes(
            @Parameter(hidden = true) Pageable pageable
    );

    @Operation(summary = "Buscar cliente por id")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(@PathVariable Long id);

    @Operation(summary = "Atualizar cliente")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable Long id, @RequestBody ClienteRequestDTO clienteRequestDTO);

    @Operation(summary = "Excluir cliente")
    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> removerCliente(@PathVariable Long id);
}