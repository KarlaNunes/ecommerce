package br.ifrn.edu.jeferson.ecommerce.service;

import br.ifrn.edu.jeferson.ecommerce.domain.Cliente;
import br.ifrn.edu.jeferson.ecommerce.domain.ItemPedido;
import br.ifrn.edu.jeferson.ecommerce.domain.Pedido;
import br.ifrn.edu.jeferson.ecommerce.domain.Produto;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoPatchDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.enums.StatusPedido;
import br.ifrn.edu.jeferson.ecommerce.exception.BusinessException;
import br.ifrn.edu.jeferson.ecommerce.exception.ResourceNotFoundException;
import br.ifrn.edu.jeferson.ecommerce.mapper.PedidoMapper;
import br.ifrn.edu.jeferson.ecommerce.repository.ClienteRepository;
import br.ifrn.edu.jeferson.ecommerce.repository.ItemPedidoRepository;
import br.ifrn.edu.jeferson.ecommerce.repository.PedidoRepository;
import br.ifrn.edu.jeferson.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public PedidoResponseDTO salvar(PedidoRequestDTO pedidoRequestDTO) {
        Pedido pedido = pedidoMapper.toEntity(pedidoRequestDTO);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatusPedido(StatusPedido.AGUARDANDO);

        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente de id %d não encontrado", pedidoRequestDTO.getClienteId())));

        pedido.setCliente(cliente);

        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedido itemPedido : pedido.getItens()) {
            Produto produto = produtoRepository.findById(itemPedido.getProduto().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Produto com id %d não encontrado", itemPedido.getProduto().getId())));
            int quantidade = itemPedido.getQuantidade();

            if (quantidade > produto.getEstoque()) {
                throw new BusinessException(String.format("Não há %s em estoque suficiente", produto.getNome()));
            }

            itemPedido.setProduto(produto);
            BigDecimal valorItem = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
            itemPedido.setValorUnitario(produto.getPreco());
            total = total.add(valorItem);
            itemPedido.setPedido(pedido);
            itemPedidoRepository.save(itemPedido);
        }
        pedido.setValorTotal(total);
        return pedidoMapper.toPedidoResponseDTO(pedidoRepository.save(pedido));
    }

    @Cacheable(
            value = "pedidos",
            key = "#pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<PedidoResponseDTO> listar(Pageable pageable, Specification<Pedido> specification) {
        return pedidoRepository.findAll(specification, pageable)
                .map(pedidoMapper::toPedidoResponseDTO);
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
            if (statusPedido == StatusPedido.PAGO) {
                atualizarEstoque(pedido);
            }
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

    public void atualizarEstoque(Pedido pedido) {
        for (ItemPedido itemPedido: pedido.getItens()) {
            int quantidade = itemPedido.getQuantidade();
            Produto produto = itemPedido.getProduto();

            if (quantidade > produto.getEstoque()) {
                throw new BusinessException(String.format("Não há %s em estoque suficiente", produto.getNome()));
            }

            produto.setEstoque(produto.getEstoque() - quantidade);
            produtoRepository.save(produto);
        }
    }
}
