package br.com.equipe4.app_produtos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @Column(name = "senha", length = 100, nullable = false)
    @Min(6)
    private String senha;

    @Column(name = "criado_em")
    private LocalDate criado_em;

    @Column(name = "atualizado_em")
    private LocalDate atualizado_em;
}
