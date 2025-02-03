package br.ifrn.edu.jeferson.ecommerce.controller;

import br.ifrn.edu.jeferson.ecommerce.controller.swagger.ClienteControllerInterface;
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

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController implements ClienteControllerInterface {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> salvarCliente(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.cadastrarCliente(clienteRequestDTO));
    }

    @GetMapping
    public ResponseEntity<CustomPage<ClienteResponseDTO>> listarClientes(
           Pageable pageable
    ) {
        Page<ClienteResponseDTO> response = clienteService.listarClientes(pageable);
        var customPageResponse = new CustomPage<>(response);
        return ResponseEntity.status(HttpStatus.OK).body(customPageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscarClientePorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable Long id, @RequestBody ClienteRequestDTO clienteRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.atualizarCliente(clienteRequestDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> removerCliente(@PathVariable Long id) {
        clienteService.removerCliente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
