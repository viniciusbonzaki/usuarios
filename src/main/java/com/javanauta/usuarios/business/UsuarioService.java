package com.javanauta.usuarios.business;

import com.javanauta.usuarios.business.converter.UsuarioConverter;
import com.javanauta.usuarios.business.dtos.UsuarioDTO;
import com.javanauta.usuarios.infrastructure.entity.Usuario;
import com.javanauta.usuarios.infrastructure.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        // Transformar o UsuárioDTO em um Usuario Entity
        Usuario usuario = usuarioConverter.converterParaUsuario(usuarioDTO);
        // Salvar o Usuário Entity no Banco de Dados (Retorno em Entity)
        usuario = usuarioRepository.save(usuario);
        // Converter o Usuário Entity para UsuárioDTO e Retornar
        return usuarioConverter.converterParaUsuarioDTO(usuario);
    }

}
