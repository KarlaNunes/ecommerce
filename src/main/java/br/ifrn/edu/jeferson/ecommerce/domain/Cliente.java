package br.ifrn.edu.jeferson.ecommerce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Email
    @Column(unique = true)
    private String email;
    @Pattern(regexp = "^(\\\\d{3})\\\\.(\\\\d{3})\\\\.(\\\\d{3})-(\\\\d{2})$", message = "CPF inválido")
    private String cpf;
    private String telefone;

    @OneToOne(mappedBy = "cliente", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Endereco endereco;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();


}
