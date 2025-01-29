package br.ifrn.edu.jeferson.ecommerce.domain.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClienteRequestDTO {
    private String nome;
    @Email(message = "E-mail inválido")
    private String email;
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF inválido")
    private String cpf;
    private String telefone;
    private EnderecoRequestDTO endereco;
}
