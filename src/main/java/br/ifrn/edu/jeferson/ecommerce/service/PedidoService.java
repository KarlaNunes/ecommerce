package br.ifrn.edu.jeferson.ecommerce.service;

import br.ifrn.edu.jeferson.ecommerce.domain.Cliente;
import br.ifrn.edu.jeferson.ecommerce.domain.Pedido;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoPatchDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.enums.StatusPedido;
import br.ifrn.edu.jeferson.ecommerce.exception.ResourceNotFoundException;
import br.ifrn.edu.jeferson.ecommerce.mapper.PedidoMapper;
import br.ifrn.edu.jeferson.ecommerce.repository.ClienteRepository;
import br.ifrn.edu.jeferson.ecommerce.repository.PedidoRepository;
import br.ifrn.edu.jeferson.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PedidoMapper pedidoMapper;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public PedidoResponseDTO salvar(PedidoRequestDTO pedidoRequestDTO) {
        Pedido pedido = pedidoMapper.toEntity(pedidoRequestDTO);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatusPedido(StatusPedido.AGUARDANDO);

        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente de id %d não encontrado", pedidoRequestDTO.getClienteId())));

        pedido.setCliente(cliente);

        BigDecimal total = pedidoRequestDTO.getItens().stream()
                .map(itemPedido -> itemPedido.getValorUnitario()
                        .multiply(BigDecimal.valueOf(itemPedido.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setValorTotal(total);
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

    public List<PedidoResponseDTO> buscarPorCliente(Long clienteId) {
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente com id %d não encontrado", clienteId)));

        return pedidoRepository.findByClienteId(clienteId)
                .stream()
                .map(pedidoMapper::toPedidoResponseDTO)
                .toList();
    }

    public PedidoResponseDTO atualizarStatus(Long id, PedidoPatchDTO pedidoPatchDTO) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Pedido com id %d não encontrado.", id)));

        try {
            StatusPedido statusPedido = StatusPedido.valueOf(pedidoPatchDTO.getStatusPedido());
            pedido.setStatusPedido(statusPedido);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status do pedido inválido.");
        }

        return pedidoMapper.toPedidoResponseDTO(pedidoRepository.save(pedido));
    }

    public void remover(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Pedido com id %d não encontrado.", id));
        }

        pedidoRepository.deleteById(id);
    }
}
