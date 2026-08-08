package com.javanauta.usuarios.business.converter;

import com.javanauta.usuarios.business.dtos.EnderecoDTO;
import com.javanauta.usuarios.business.dtos.TelefoneDTO;
import com.javanauta.usuarios.business.dtos.UsuarioDTO;
import com.javanauta.usuarios.infrastructure.entity.Endereco;
import com.javanauta.usuarios.infrastructure.entity.Telefone;

import com.javanauta.usuarios.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioConverter {
    // Converter um UsuarioDTO para um Usuario Entity
    public Usuario converterParaUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(converterParaListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(converterParaListaTelefone(usuarioDTO.getTelefones()))
                .build();
    }

    // Converter uma Lista de EnderecoDTO para uma Lista de Endereco Entity
    public List<Endereco> converterParaListaEndereco(List<EnderecoDTO> listaEnderecoDTO) {
        List<Endereco> listaEndereco = new ArrayList<>();

        for(EnderecoDTO enderecoDTO : listaEnderecoDTO) {
            listaEndereco.add(converterParaEndereco(enderecoDTO));
        }

        return listaEndereco;
    }

    // Converter um EnderecoDTO para um Endereco Entity
    public Endereco converterParaEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .estado(enderecoDTO.getEstado())
                .cidade(enderecoDTO.getCidade())
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cep(enderecoDTO.getCep())
                .complemento(enderecoDTO.getComplemento())
                .build();
    }

    // Converter uma Lista de TelefoneDTO para uma Lista de Telefone Entity
    public List<Telefone> converterParaListaTelefone(List<TelefoneDTO> listaTelefoneDTO) {
        return listaTelefoneDTO.stream().map(this::converterParaTelefone).toList();
    }

    // Converter um TelefoneDTO para um Telefone Entity
    public Telefone converterParaTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }



    // Converter um Usuario Entity para um UsuarioDTO
    public UsuarioDTO converterParaUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(converterParaListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(converterParaListaTelefoneDTO(usuario.getTelefones()))
                .build();
    }

    // Converter uma Lista de Endereco Entity para uma Lista de EnderecoDTO
    public List<EnderecoDTO> converterParaListaEnderecoDTO(List<Endereco> listaEndereco) {
        List<EnderecoDTO> listaEnderecoDTO = new ArrayList<>();

        for(Endereco endereco : listaEndereco) {
            listaEnderecoDTO.add(converterParaEnderecoDTO(endereco));
        }

        return listaEnderecoDTO;
    }

    // Converter um Endereco Entity para um EnderecoDTO
    public EnderecoDTO converterParaEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .estado(endereco.getEstado())
                .cidade(endereco.getCidade())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cep(endereco.getCep())
                .complemento(endereco.getComplemento())
                .build();
    }

    // Converter uma Lista de Telefone Entity para uma Lista de TelefoneDTO
    public List<TelefoneDTO> converterParaListaTelefoneDTO(List<Telefone> listaTelefone) {
        return listaTelefone.stream().map(this::converterParaTelefoneDTO).toList();
    }

    // Converter um Telefone Entity para um TelefoneDTO
    public TelefoneDTO converterParaTelefoneDTO(Telefone telefone) {
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }



    // Atualizar os Dados do Usuário que forem passados na Requisição
    public Usuario atualizarUsuario(UsuarioDTO usuarioDTO, Usuario usuarioEntity) {
        return Usuario.builder()
                .id(usuarioEntity.getId())
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : usuarioEntity.getNome())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : usuarioEntity.getEmail())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : usuarioEntity.getSenha())
                .enderecos(usuarioEntity.getEnderecos())
                .telefones(usuarioEntity.getTelefones())
                .build();
    }

    // Atualizar os Dados do Endereco que forem passados na Requisição
    public Endereco atualizarEndereco(EnderecoDTO enderecoDTO, Endereco enderecoEntity) {
        return Endereco.builder()
                .id(enderecoEntity.getId())
                .estado(enderecoDTO.getEstado() != null ? enderecoDTO.getEstado() : enderecoEntity.getEstado())
                .cidade(enderecoDTO.getCidade() != null ? enderecoDTO.getCidade() : enderecoEntity.getCidade())
                .rua(enderecoDTO.getRua() != null ? enderecoDTO.getRua() : enderecoEntity.getRua())
                .numero(enderecoDTO.getNumero() != null ? enderecoDTO.getNumero() : enderecoEntity.getNumero())
                .cep(enderecoDTO.getCep() != null ? enderecoDTO.getCep() : enderecoEntity.getCep())
                .complemento(enderecoDTO.getComplemento() != null ? enderecoDTO.getComplemento() : enderecoEntity.getComplemento())
                .build();
    }

    // Atualizar os Dados do Telefone que forem passados na Requisição
    public Telefone atualizarTelefone(TelefoneDTO telefoneDTO, Telefone telefoneEntity) {
        return Telefone.builder()
                .id(telefoneEntity.getId())
                .numero(telefoneDTO.getNumero() != null ? telefoneDTO.getNumero() : telefoneEntity.getNumero())
                .ddd(telefoneDTO.getDdd() != null ? telefoneDTO.getDdd() : telefoneEntity.getDdd())
                .build();
    }


    // Método que será utilizado para adicionar um Endereço através do ID do Usuário
    public Endereco converterParaEnderecoEntity(EnderecoDTO enderecoDTO, Long idUsuario) {
        return Endereco.builder()
                .estado(enderecoDTO.getEstado())
                .cidade(enderecoDTO.getCidade())
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cep(enderecoDTO.getCep())
                .complemento(enderecoDTO.getComplemento())
                .id_usuario(idUsuario)
                .build();
    }

    // Método que será utilizado para adicionar um Telefone através do ID do Usuário
    public Telefone converterParaTelefoneEntity(TelefoneDTO telefoneDTO, Long idUsuario) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .id_usuario(idUsuario)
                .build();
    }
}
