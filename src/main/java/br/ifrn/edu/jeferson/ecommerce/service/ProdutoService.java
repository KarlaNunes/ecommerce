package br.ifrn.edu.jeferson.ecommerce.service;

import br.ifrn.edu.jeferson.ecommerce.domain.Produto;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.exception.ResourceNotFoundException;
import br.ifrn.edu.jeferson.ecommerce.mapper.ProdutoMapper;
import br.ifrn.edu.jeferson.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ProdutoMapper produtoMapper;

    public ProdutoResponseDTO salvar(ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoMapper.toEntity(produtoRequestDTO);
        return produtoMapper.toProdutoResponseDTO(produtoRepository.save(produto));
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " não encontrado"));

        return produtoMapper.toProdutoResponseDTO(produto);
    }

    public List<ProdutoResponseDTO> listar() {
        return produtoRepository.findAll()
                .stream()
                .map(produtoMapper::toProdutoResponseDTO)
                .toList();
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " não encontrado"));

        produto = produtoMapper.updateEntityFromDTO(produto, produtoRequestDTO);

        return produtoMapper.toProdutoResponseDTO(produto);
    }

    public void remover(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto com id " + id + " não encontrado");
        }
        produtoRepository.deleteById(id);
    }
    
//    public ProdutoRequestDTO atualizarEstoque(Long id, ProdutoRequestDTO produtoRequestDTO) {
//        Produto produto = produtoRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " não encontrado"));
//
//
//    }
}
