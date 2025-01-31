package br.ifrn.edu.jeferson.ecommerce.service;

import br.ifrn.edu.jeferson.ecommerce.domain.Cliente;
import br.ifrn.edu.jeferson.ecommerce.domain.Endereco;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.EnderecoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.exception.BusinessException;
import br.ifrn.edu.jeferson.ecommerce.exception.ResourceNotFoundException;
import br.ifrn.edu.jeferson.ecommerce.mapper.ClienteMapper;
import br.ifrn.edu.jeferson.ecommerce.mapper.EnderecoMapper;
import br.ifrn.edu.jeferson.ecommerce.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new BusinessException(String.format("Já existe um cliente cadastrado com esse e-mail: %s", cliente.getEmail()));
        }

        if (clienteRepository.findByCpf(cliente.getCpf()).isPresent()) {
            throw new BusinessException(String.format("Já existe um cliente com esse cpf: %s", cliente.getCpf()));
        }
        return clienteMapper.toClienteResponseDTO(clienteRepository.save(cliente));
    }

    public Page<ClienteResponseDTO> listarClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable)
                .map(clienteMapper::toClienteResponseDTO);
    }

    public ClienteResponseDTO buscarClientePorId(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente de id %d não encontrado", clienteId)));
        return clienteMapper.toClienteResponseDTO(cliente);
    }

    public ClienteResponseDTO atualizarCliente(ClienteRequestDTO clienteRequestDTO, Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente com id %d não encontrado" , clienteId)));

        if (!cliente.getEmail().equals(clienteRequestDTO.getEmail()) && clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new BusinessException(String.format("Já existe um cliente com esse e-mail: %s", cliente.getEmail()));
        }

        if (clienteRepository.findByCpf(cliente.getCpf()).isPresent() && !cliente.getCpf().equals(clienteRequestDTO.getCpf())) {
            throw new BusinessException(String.format("Já existe um cliente com esse cpf: %s", cliente.getCpf()));
        }

        Endereco endereco = enderecoMapper.toEntity(clienteRequestDTO.getEndereco());
        endereco.setCliente(cliente);
        cliente.setEndereco(endereco);
        cliente = clienteMapper.updateClienteFromDTO(clienteRequestDTO, cliente);
        return clienteMapper.toClienteResponseDTO(clienteRepository.save(cliente));
    }

    public void removerCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(String.format("Cliente com id %d não encontrado" , clienteId))));
        if (!cliente.getPedidos().isEmpty()) {
            throw new IllegalStateException("Não é possível deletar o cliente, pois ele possui pedidos associados.");
        }
        clienteRepository.deleteById(clienteId);
    }
}
