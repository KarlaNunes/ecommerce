package br.ifrn.edu.jeferson.ecommerce.controller.swagger;

import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.EnderecoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.EnderecoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Endereços", description = "API de gerenciamento de endereços")
public interface EnderecoControllerInterface {
    @Operation(summary = "Criar um novo endereço")
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarEndereco(@PathVariable Long clienteId, @RequestBody EnderecoRequestDTO enderecoRequestDTO);

    @Operation(summary = "Buscar endereço pelo id")
    @GetMapping
    public ResponseEntity<EnderecoResponseDTO> buscarEndereco(@PathVariable Long clienteId);

    @Operation(summary = "Atualizar endereço")
    @PutMapping
    public ResponseEntity<ClienteResponseDTO> atualizarEndereco(@PathVariable Long clienteId, @RequestBody EnderecoRequestDTO enderecoRequestDTO);

    @Operation(summary = "Excluir endereço")
    @DeleteMapping
    public ResponseEntity<Void> removerEndereco(@PathVariable Long clienteId);
}
