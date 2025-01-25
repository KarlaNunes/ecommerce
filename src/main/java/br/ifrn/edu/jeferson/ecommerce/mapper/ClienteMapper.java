package br.ifrn.edu.jeferson.ecommerce.mapper;

import br.ifrn.edu.jeferson.ecommerce.domain.Cliente;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = EnderecoMapper.class)
public interface ClienteMapper {
    @Mapping(target = "endereco", source = "endereco")
    ClienteResponseDTO toClienteResponseDTO(Cliente cliente);

    Cliente toEntity(ClienteRequestDTO clienteRequestDTO);

    @Mapping(target = "id", ignore = true)
    Cliente updateClienteFromDTO(ClienteRequestDTO clienteRequestDTO, @MappingTarget Cliente cliente);
}
