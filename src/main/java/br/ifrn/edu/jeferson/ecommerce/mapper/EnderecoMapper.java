package br.ifrn.edu.jeferson.ecommerce.mapper;

import br.ifrn.edu.jeferson.ecommerce.domain.Endereco;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.EnderecoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.EnderecoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    EnderecoResponseDTO toEnderecoResponseDTO(Endereco endereco);

    @Mapping(target = "id", ignore = true)
    Endereco toEntity(EnderecoRequestDTO enderecoRequestDTO);

    @Mapping(target = "id", ignore = true)
    Endereco updateEntityFromDTO(EnderecoRequestDTO enderecoRequestDTO, @MappingTarget Endereco endereco);
}
