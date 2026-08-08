package com.javanauta.usuarios.business;

import com.javanauta.usuarios.business.converter.UsuarioConverter;
import com.javanauta.usuarios.business.dtos.UsuarioDTO;
import com.javanauta.usuarios.infrastructure.entity.Usuario;
import com.javanauta.usuarios.infrastructure.exceptions.ConflictException;
import com.javanauta.usuarios.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuarios.infrastructure.repository.UsuarioRepository;

import com.javanauta.usuarios.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Método para Salvar os Dados do Usuário no Banco
    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        emailExistente(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        // Transformar o UsuárioDTO em um Usuario Entity
        Usuario usuario = usuarioConverter.converterParaUsuario(usuarioDTO);
        // Salvar o Usuário Entity no Banco de Dados (Retorno em Entity)
        usuario = usuarioRepository.save(usuario);
        // Converter o Usuário Entity para UsuárioDTO e Retornar
        return usuarioConverter.converterParaUsuarioDTO(usuario);
    }

    // Método para verificar se o Email já existe no Banco e definir as Exceções
    public void emailExistente(String email) {
        try {
            boolean emailExiste = verificarSeEmailExiste(email);

            if (emailExiste) {
                throw new ConflictException("Email já cadastrado " + email);
            }
        }
        catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado ", e.getCause());
        }
    }

    // Verificar se o Email já existe utilizando o Método "existsByEmail" de UsuarioRepository
    public boolean verificarSeEmailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Buscar um Usuário pelo Email utilizando o Método "findByEmail" de UsuarioRepository
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email)
        );
    }

    // Método para deletar um Usuário pelo Email
    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    // Método para atualizar os dadods de um Usuário pelo Email
    public UsuarioDTO atualizarDadosUsuario(String token, UsuarioDTO usuarioDTO) {
        // Extrair o Email do Usuário através do JWT Token
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        // Encontrar o Usuário Entity através do Email
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email)
        );

        // Atualizar os Dados do Usuário comparando com os que foram passados
        Usuario usuario = usuarioConverter.atualizarUsuario(usuarioDTO, usuarioEntity);

        // Salvar os Dados no Banco de Dados
        return usuarioConverter.converterParaUsuarioDTO(usuarioRepository.save(usuario));
    }
}
