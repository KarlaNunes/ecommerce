package br.ifrn.edu.jeferson.ecommerce.service;

import br.ifrn.edu.jeferson.ecommerce.domain.Produto;
import br.ifrn.edu.jeferson.ecommerce.domain.ProdutoCategoria;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoPatchDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.exception.ResourceNotFoundException;
import br.ifrn.edu.jeferson.ecommerce.mapper.ProdutoMapper;
import br.ifrn.edu.jeferson.ecommerce.repository.ProdutoCategoriaRepository;
import br.ifrn.edu.jeferson.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ProdutoMapper produtoMapper;
    @Autowired
    private ProdutoCategoriaRepository produtoCategoriaRepository;

    public ProdutoResponseDTO salvar(ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoMapper.toEntity(produtoRequestDTO);
        return produtoMapper.toProdutoResponseDTO(produtoRepository.save(produto));
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " não encontrado"));

        return produtoMapper.toProdutoResponseDTO(produto);
    }

    public Page<ProdutoResponseDTO> listar(Pageable pageable) {
        return produtoRepository.findAll(pageable)
                .map(produtoMapper::toProdutoResponseDTO);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " não encontrado"));

        produto = produtoMapper.updateEntityFromDTO(produto, produtoRequestDTO);

        return produtoMapper.toProdutoResponseDTO(produtoRepository.save(produto));
    }

    public void remover(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto com id " + id + " não encontrado");
        }
        produtoRepository.deleteById(id);
    }
    
    public ProdutoResponseDTO atualizarEstoque(Long id, ProdutoPatchDTO produtoPatchDTO) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " não encontrado"));

        produto.setEstoque(produtoPatchDTO.getEstoque());
        return produtoMapper.toProdutoResponseDTO(produtoRepository.save(produto));
    }

    public List<ProdutoResponseDTO> listarPorCategoria(Long categoriaId) {
        List<Produto> produtos = produtoCategoriaRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(ProdutoCategoria::getProduto)
                .toList();
        return produtos.stream().map(produtoMapper::toProdutoResponseDTO).collect(Collectors.toList());
    }
}
