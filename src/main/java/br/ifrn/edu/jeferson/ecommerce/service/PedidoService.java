package br.ifrn.edu.jeferson.ecommerce.service;

import br.ifrn.edu.jeferson.ecommerce.domain.Pedido;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoPatchDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.enums.StatusPedido;
import br.ifrn.edu.jeferson.ecommerce.exception.ResourceNotFoundException;
import br.ifrn.edu.jeferson.ecommerce.mapper.PedidoMapper;
import br.ifrn.edu.jeferson.ecommerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PedidoMapper pedidoMapper;

    public PedidoResponseDTO salvar(PedidoRequestDTO pedidoRequestDTO) {
        Pedido pedido = pedidoMapper.toEntity(pedidoRequestDTO);
        return pedidoMapper.toPedidoResponseDTO(pedidoRepository.save(pedido));
    }

    public List<PedidoResponseDTO> listar() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toPedidoResponseDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        return pedidoMapper.toPedidoResponseDTO(pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Pedido com id %d não encontrado.", id))));
    }

    public PedidoResponseDTO atualizarStatus(Long id, PedidoPatchDTO pedidoPatchDTO) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Pedido com id %d não encontrado.", id)));

        StatusPedido statusPedido = StatusPedido.valueOf(pedidoPatchDTO.getStatusPedido());
        pedido.setStatusPedido(statusPedido);
        return pedidoMapper.toPedidoResponseDTO(pedidoRepository.save(pedido));
    }

    public void remover(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Pedido com id %d não encontrado.", id));
        }

        pedidoRepository.deleteById(id);
    }
}
