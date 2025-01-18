package br.ifrn.edu.jeferson.ecommerce.mapper;

import br.ifrn.edu.jeferson.ecommerce.domain.Categoria;
import br.ifrn.edu.jeferson.ecommerce.domain.Produto;
import br.ifrn.edu.jeferson.ecommerce.domain.ProdutoCategoria;
import br.ifrn.edu.jeferson.ecommerce.domain.ProdutoCategoriaId;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.repository.ProdutoRepository;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaResponseDTO toResponseDTO(Categoria categoria);

    // Converter DTO para Categoria
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    Categoria toEntity(CategoriaRequestDTO dto);

    List<CategoriaResponseDTO> toDTOList(List<Categoria> categorias);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    void updateEntityFromDTO(CategoriaRequestDTO dto, @MappingTarget Categoria categoria);

    @Mapping(target = "id", source = "produto.id")
    @Mapping(target = "nome", source = "produto.nome")
    @Mapping(target = "descricao", source = "produto.descricao")
    @Mapping(target = "preco", source = "produto.preco")
    @Mapping(target = "estoque", source = "produto.estoque")
    ProdutoResponseDTO produtoToProdutoResponseDTO(ProdutoCategoria produtoCategoria);

    List<ProdutoResponseDTO> mapProdutoCategoriaToProdutoResponseDTOList(List<ProdutoCategoria> produtoCategorias);
}
