package br.ifrn.edu.jeferson.ecommerce.domain;

import br.ifrn.edu.jeferson.ecommerce.domain.enums.StatusPedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataPedido;
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido")
    @Size(min = 1, message = "Quantidade de itens deve ser maior que zero")
    private List<ItemPedido> itens = new ArrayList<>();


}
