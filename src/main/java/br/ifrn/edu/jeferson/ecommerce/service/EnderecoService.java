package br.ifrn.edu.jeferson.ecommerce.service;

import br.ifrn.edu.jeferson.ecommerce.domain.Cliente;
import br.ifrn.edu.jeferson.ecommerce.domain.Endereco;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.EnderecoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.EnderecoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.exception.ResourceNotFoundException;
import br.ifrn.edu.jeferson.ecommerce.mapper.ClienteMapper;
import br.ifrn.edu.jeferson.ecommerce.mapper.EnderecoMapper;
import br.ifrn.edu.jeferson.ecommerce.repository.ClienteRepository;
import br.ifrn.edu.jeferson.ecommerce.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private EnderecoMapper enderecoMapper;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ClienteMapper clienteMapper;

    public ClienteResponseDTO salvar(EnderecoRequestDTO enderecoRequestDTO, Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente com id %d não encontrado", clienteId)));
        Endereco endereco = enderecoMapper.toEntity(enderecoRequestDTO);
        endereco.setCliente(cliente);
        cliente.setEndereco(endereco);
        EnderecoResponseDTO enderecoResponseDTO = enderecoMapper.toEnderecoResponseDTO(enderecoRepository.save(endereco));
        return clienteMapper.toClienteResponseDTO(cliente);
    }

    public EnderecoResponseDTO buscarEnderecoDoCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente com id %d não encontrado", clienteId)));

        return Optional.ofNullable(cliente.getEndereco())
                .map(enderecoMapper::toEnderecoResponseDTO)
                .orElse(new EnderecoResponseDTO());
    }

    public ClienteResponseDTO atualizar(EnderecoRequestDTO enderecoRequestDTO, Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente com id %d não encontrado", clienteId)));

        Endereco endereco = Optional.ofNullable(cliente.getEndereco())
                .map(enderecoExistente -> enderecoMapper.updateEntityFromDTO(enderecoRequestDTO, enderecoExistente))
                .orElseGet(() -> {
                    Endereco novoEndereco = enderecoMapper.toEntity(enderecoRequestDTO);
                    novoEndereco.setCliente(cliente);
                    return novoEndereco;
                });

        enderecoRepository.save(endereco);
        return clienteMapper.toClienteResponseDTO(cliente);
    }

    public void remover(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente com id %d não encontrado", clienteId)));

        if (cliente.getEndereco() != null) {
            Long enderecoId = cliente.getEndereco().getId();
            cliente.setEndereco(null);
            enderecoRepository.deleteById(enderecoId);
        } else {
            throw new IllegalStateException(String.format("Cliente de id %d não possui endereço para excluir", clienteId));
        }



    }


}
