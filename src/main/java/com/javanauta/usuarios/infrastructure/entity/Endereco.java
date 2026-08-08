package com.javanauta.usuarios.infrastructure.entity;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enderecos")
@Builder
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado", length = 2)
    private String estado;
    @Column(name = "cidade", length = 150)
    private String cidade;
    @Column(name = "rua")
    private String rua;
    @Column(name = "numero")
    private Long numero;
    @Column(name = "cep", length = 9)
    private String cep;
    @Column(name = "complemento", length = 10)
    private String complemento;
}
