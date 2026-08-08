package com.javanauta.usuarios.infrastructure.repository;

import com.javanauta.usuarios.infrastructure.entity.Usuario;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método do JPA Repository para verificar se o Email existe
    boolean existsByEmail(String email);

    // Método do JPA Repository para encontrar um Usuário pelo Email
    // Optinal para evitar quebrar o Programa caso o Email não seja encontrado
    Optional<Usuario> findByEmail(String email);

    // Método do JPA Repository para deletar um Usuário pelo Email
    // Transactional é obrigatorio em Deletes para evitar problemas ao deletar
    @Transactional
    void deleteByEmail(String email);
}
