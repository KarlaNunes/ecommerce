package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import lombok.Data;

@Data
public class EnderecoRequestDTO {
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
