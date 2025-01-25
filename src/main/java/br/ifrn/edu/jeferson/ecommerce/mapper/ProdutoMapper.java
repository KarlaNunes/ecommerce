package br.ifrn.edu.jeferson.ecommerce.mapper;

import br.ifrn.edu.jeferson.ecommerce.domain.Produto;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    ProdutoResponseDTO toProdutoResponseDTO(Produto produto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    Produto toEntity(ProdutoRequestDTO produtoRequestDTO);

    @Mapping(target = "categorias", ignore = true)
    @Mapping(target = "id", ignore = true)
    Produto updateEntityFromDTO(@MappingTarget Produto produto, ProdutoRequestDTO produtoRequestDTO);
}
