package com.javanauta.usuarios.business;

import com.javanauta.usuarios.business.converter.UsuarioConverter;
import com.javanauta.usuarios.business.dtos.EnderecoDTO;
import com.javanauta.usuarios.business.dtos.TelefoneDTO;
import com.javanauta.usuarios.business.dtos.UsuarioDTO;
import com.javanauta.usuarios.infrastructure.entity.Endereco;
import com.javanauta.usuarios.infrastructure.entity.Telefone;
import com.javanauta.usuarios.infrastructure.entity.Usuario;
import com.javanauta.usuarios.infrastructure.exceptions.ConflictException;
import com.javanauta.usuarios.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuarios.infrastructure.repository.EnderecoRepository;
import com.javanauta.usuarios.infrastructure.repository.TelefoneRepository;
import com.javanauta.usuarios.infrastructure.repository.UsuarioRepository;
import com.javanauta.usuarios.infrastructure.security.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
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
    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.converterParaUsuarioDTO(
                    usuarioRepository.findByEmail(email).orElseThrow(
                            () -> new ResourceNotFoundException("Email não encontrado " + email)
                    )
            );
        }
        catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado ", e.getCause());
        }
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

    // Método para atualizar os dados de um Endereço pelo ID
    public EnderecoDTO atualizarEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {
        // Encontrar o Endereço à ser Alterado pelo ID
        Endereco enderecoEntity = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundException("ID do Endereço não encontrado " + idEndereco)
        );

        // Atualizar os Dados do Endereço comparando com os que foram passados
        Endereco endereco = usuarioConverter.atualizarEndereco(enderecoDTO, enderecoEntity);

        // Salvar os Dados no Banco de Dados
        return usuarioConverter.converterParaEnderecoDTO(enderecoRepository.save(endereco));
    }

    // Método para atualizar os dados de um Telefone pelo ID
    public TelefoneDTO atualizarTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {
        // Encontrar o Telefone à ser Alterado pelo ID
        Telefone telefoneEntity = telefoneRepository.findById(idTelefone).orElseThrow(
                () -> new ResourceNotFoundException("ID do Telefone não encontrado " + idTelefone)
        );

        // Atualizar os Dados do Telefone comparando com os que foram passados
        Telefone telefone = usuarioConverter.atualizarTelefone(telefoneDTO, telefoneEntity);

        // Salvar os Dados no Banco de Dados
        return usuarioConverter.converterParaTelefoneDTO(telefoneRepository.save(telefone));
    }

    // Método para adicionar um novo Endereço à um Usuário pelo seu Email
    public EnderecoDTO cadastrarEndereco(String token, EnderecoDTO enderecoDTO) {
        // Pegar o Email do Usuário que iremos adicionar um Endereco
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        // Pegar o Usuário que iremos adicionar um Endereco através do Email
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email)
        );

        // Criar o Novo Endereço à ser adicionado, relacionando o ID do Usuário
        Endereco endereco = usuarioConverter.converterParaEnderecoEntity(enderecoDTO, usuario.getId());

        // Salvar o Endereço no Dados no Banco de Dados
        return usuarioConverter.converterParaEnderecoDTO(enderecoRepository.save(endereco));
    }

    // Método para adicionar um novo Telefone à um Usuário pelo seu Email
    public TelefoneDTO cadastrarTelefone(String token, TelefoneDTO telefoneDTO) {
        // Pegar o Email do Usuário que iremos adicionar um Telefone
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        // Pegar o Usuário que iremos adicionar um Telefone através do Email
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email)
        );

        // Criar o Novo Telefone à ser adicionado, relacionando o ID do Usuário
        Telefone telefone = usuarioConverter.converterParaTelefoneEntity(telefoneDTO, usuario.getId());

        // Salvar o Telefone no Banco de Dados
        return usuarioConverter.converterParaTelefoneDTO(telefoneRepository.save(telefone));
    }
}
