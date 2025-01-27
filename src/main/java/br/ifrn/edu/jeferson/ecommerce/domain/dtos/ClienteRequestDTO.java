package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import br.ifrn.edu.jeferson.ecommerce.domain.Endereco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClienteRequestDTO {
    private String nome;
    @Email
    private String email;
    @Pattern(regexp = "^(\\\\d{3})\\\\.(\\\\d{3})\\\\.(\\\\d{3})-(\\\\d{2})$", message = "CPF inválido")
    private String cpf;
    private String telefone;
    private EnderecoRequestDTO endereco;
}
