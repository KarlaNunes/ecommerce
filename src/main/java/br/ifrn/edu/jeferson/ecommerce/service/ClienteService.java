package br.ifrn.edu.jeferson.ecommerce.service;

import br.ifrn.edu.jeferson.ecommerce.domain.Cliente;
import br.ifrn.edu.jeferson.ecommerce.domain.Endereco;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.EnderecoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.exception.ResourceNotFoundException;
import br.ifrn.edu.jeferson.ecommerce.mapper.ClienteMapper;
import br.ifrn.edu.jeferson.ecommerce.mapper.EnderecoMapper;
import br.ifrn.edu.jeferson.ecommerce.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;
    @Autowired
    private EnderecoMapper enderecoMapper;

    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteRequestDTO);
        Endereco endereco = enderecoMapper.toEntity(clienteRequestDTO.getEndereco());
        endereco.setCliente(cliente);
        cliente.setEndereco(endereco);
        return clienteMapper.toClienteResponseDTO(clienteRepository.save(cliente));
    }

    public List<ClienteResponseDTO> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toClienteResponseDTO)
                .toList();
    }

    public ClienteResponseDTO buscarClientePorId(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente de id %d não encontrado", clienteId)));
        return clienteMapper.toClienteResponseDTO(cliente);
    }

    public ClienteResponseDTO atualizarCliente(ClienteRequestDTO clienteRequestDTO, Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente com id %d não encontrado" , clienteId)));

        Endereco endereco = enderecoMapper.toEntity(clienteRequestDTO.getEndereco());
        endereco.setCliente(cliente);
        cliente.setEndereco(endereco);
        cliente = clienteMapper.updateClienteFromDTO(clienteRequestDTO, cliente);
        return clienteMapper.toClienteResponseDTO(clienteRepository.save(cliente));
    }

    public void removerCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new ResourceNotFoundException(String.format(String.format("Cliente com id %d não encontrado" , clienteId)));
        }
        clienteRepository.deleteById(clienteId);
    }
}
