package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import lombok.Data;

@Data
public class ClienteResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private EnderecoResponseDTO endereco;
}
