package br.ifrn.edu.jeferson.ecommerce.mapper;

import br.ifrn.edu.jeferson.ecommerce.domain.ItemPedido;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ItemPedidoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ItemPedidoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto.id", source = "produtoId")
    ItemPedido toEntity(ItemPedidoRequestDTO itemPedidoRequestDTO);

    ItemPedidoResponseDTO toItemPedidoResponseDTO(ItemPedido itemPedido);

    @Mapping(target = "id", ignore = true)
    ItemPedido updateEntityFromDTO(ItemPedidoRequestDTO itemPedidoRequestDTO, @MappingTarget ItemPedido itemPedido);
}
