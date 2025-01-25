package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import br.ifrn.edu.jeferson.ecommerce.domain.Endereco;
import lombok.Data;

@Data
public class ClienteRequestDTO {
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private EnderecoRequestDTO endereco;
}
