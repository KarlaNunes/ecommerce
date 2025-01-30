package br.ifrn.edu.jeferson.ecommerce.controller;

import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.EnderecoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.EnderecoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes/{clienteId}/enderecos")
@Tag(name = "Endereços", description = "API de gerenciamento de endereços")
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @Operation(summary = "Criar um novo endereço")
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarEndereco(@PathVariable Long clienteId, @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        ClienteResponseDTO clienteResponseDTO = enderecoService.salvar(enderecoRequestDTO, clienteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDTO);
    }

    @Operation(summary = "Buscar endereço pelo id")
    @GetMapping
    public ResponseEntity<EnderecoResponseDTO> buscarEndereco(@PathVariable Long clienteId) {
        EnderecoResponseDTO enderecoResponseDTO = enderecoService.buscarEnderecoDoCliente(clienteId);
        return ResponseEntity.status(HttpStatus.OK).body(enderecoResponseDTO);
    }

    @Operation(summary = "Atualizar endereço")
    @PutMapping
    public ResponseEntity<ClienteResponseDTO> atualizarEndereco(@PathVariable Long clienteId, @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        ClienteResponseDTO clienteResponseDTO = enderecoService.atualizar(enderecoRequestDTO, clienteId);
        return ResponseEntity.status(HttpStatus.OK).body(clienteResponseDTO);
    }

    @Operation(summary = "Excluir endereço")
    @DeleteMapping
    public ResponseEntity<Void> removerEndereco(@PathVariable Long clienteId) {
        enderecoService.remover(clienteId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}