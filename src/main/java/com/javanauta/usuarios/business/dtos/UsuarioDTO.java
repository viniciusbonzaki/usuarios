package com.javanauta.usuarios.business.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private String nome;
    private String senha;
    private String email;
    private List<EnderecoDTO> enderecos;
    private List<TelefoneDTO> telefones;
}
