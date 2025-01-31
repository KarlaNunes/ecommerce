package br.ifrn.edu.jeferson.ecommerce.service;

import br.ifrn.edu.jeferson.ecommerce.domain.Categoria;
import br.ifrn.edu.jeferson.ecommerce.domain.Produto;
import br.ifrn.edu.jeferson.ecommerce.domain.ProdutoCategoria;
import br.ifrn.edu.jeferson.ecommerce.domain.ProdutoCategoriaId;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoPatchDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.exception.BusinessException;
import br.ifrn.edu.jeferson.ecommerce.exception.ResourceNotFoundException;
import br.ifrn.edu.jeferson.ecommerce.mapper.CategoriaMapper;
import br.ifrn.edu.jeferson.ecommerce.repository.CategoriaRepository;
import br.ifrn.edu.jeferson.ecommerce.repository.ProdutoCategoriaRepository;
import br.ifrn.edu.jeferson.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper mapper;
    @Autowired
    private CategoriaMapper categoriaMapper;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ProdutoCategoriaRepository produtoCategoriaRepository;

    public CategoriaResponseDTO salvar(CategoriaRequestDTO categoriaDto) {
        var categoria =  mapper.toEntity(categoriaDto);

        if (categoriaRepository.existsByNome(categoria.getNome())) {
            throw new BusinessException("Já existe uma categoria com esse nome");
        }

        categoriaRepository.save(categoria);
        return mapper.toResponseDTO(categoria);
    }

    public Page<CategoriaResponseDTO> lista(Pageable pageable) {
        return categoriaRepository.findAll(pageable).map(mapper::toResponseDTO);
    }

    public void deletar(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Categoria de id " + id + " não encontrada")
        );

        if (!categoria.getProdutos().isEmpty()){
            throw new BusinessException("Não é possível excluir categoria com produtos associados");
        }

        categoriaRepository.deleteById(id);
    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO categoriaDto) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Categoria não encontrada"));

        if (!categoria.getNome().equals(categoriaDto.getNome()) && categoriaRepository.existsByNome( categoriaDto.getNome()) ) {
            throw  new BusinessException("Já existe uma categoria com esse nome");
        }

        categoriaMapper.updateEntityFromDTO(categoriaDto, categoria);
        var categoriaAlterada = categoriaRepository.save(categoria);

        return categoriaMapper.toResponseDTO(categoriaAlterada);
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Categoria não encontrada"));
        return categoriaMapper.toResponseDTO(categoria);
    }

    public CategoriaResponseDTO associarProduto(Long produtoId, Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId).orElseThrow(
                () -> new ResourceNotFoundException("Categoria de id " + categoriaId + " não encontrada")
        );

        Produto produto = produtoRepository.findById(produtoId).orElseThrow(
                () -> new ResourceNotFoundException("Produto com id " + produtoId + " não encontrado")
        );

        ProdutoCategoriaId id = new ProdutoCategoriaId(produtoId, categoriaId);

        if (produtoCategoriaRepository.existsById(id)) {
            throw new BusinessException("Association already exists");
        } else {
            ProdutoCategoria produtoCategoria = new ProdutoCategoria(id, produto, categoria);
            produtoCategoriaRepository.save(produtoCategoria);

            List<ProdutoCategoria> produtoCategorias = produtoCategoriaRepository.findByCategoriaId(categoriaId);
            List<ProdutoResponseDTO> produtoResponseDTOS = categoriaMapper.mapProdutoCategoriaToProdutoResponseDTOList(produtoCategorias);

            return new CategoriaResponseDTO(
                    categoria.getId(),
                    categoria.getNome(),
                    categoria.getDescricao(),
                    produtoResponseDTOS
            );
        }
    }

    public CategoriaResponseDTO removerProduto(Long produtoId, Long categoriaId) {
        produtoRepository.findById(produtoId).orElseThrow(
                () -> new ResourceNotFoundException("Produto com id " + produtoId + " não encontrado")
        );

        Categoria categoria = categoriaRepository.findById(categoriaId).orElseThrow(
                () -> new ResourceNotFoundException("Categoria de id " + categoriaId + " não encontrada")
        );

        ProdutoCategoriaId id = new ProdutoCategoriaId(produtoId, categoriaId);

        if (!produtoCategoriaRepository.existsById(id)) {
            throw new BusinessException("Produto de id " + produtoId + " não está associado à categoria de id " + categoriaId);
        } else {
            produtoCategoriaRepository.deleteById(id);
            List<ProdutoCategoria> produtoCategorias = produtoCategoriaRepository.findByCategoriaId(categoriaId);
            List<ProdutoResponseDTO> produtoResponseDTOS = categoriaMapper.mapProdutoCategoriaToProdutoResponseDTOList(produtoCategorias);

            return new CategoriaResponseDTO(
                    categoria.getId(),
                    categoria.getNome(),
                    categoria.getDescricao(),
                    produtoResponseDTOS
            );
        }
    }
}
