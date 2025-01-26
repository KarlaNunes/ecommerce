package br.ifrn.edu.jeferson.ecommerce.mapper;

import br.ifrn.edu.jeferson.ecommerce.domain.Pedido;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
    @Mapping(target = "id", ignore = true)
    Pedido toEntity(PedidoRequestDTO pedidoRequestDTO);

    PedidoResponseDTO toPedidoResponseDTO(Pedido pedido);

    Pedido updateEntityFromDto(PedidoRequestDTO pedidoRequestDTO, @MappingTarget Pedido pedido);
}
