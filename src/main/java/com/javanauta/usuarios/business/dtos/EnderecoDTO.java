package com.javanauta.usuarios.business.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoDTO {
    private String estado;
    private String cidade;
    private String rua;
    private Long numero;
    private String cep;
    private String complemento;
}
