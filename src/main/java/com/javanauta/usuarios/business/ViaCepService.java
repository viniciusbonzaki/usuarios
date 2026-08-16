package com.javanauta.usuarios.business;

import com.javanauta.usuarios.business.dtos.ViaCepDTO;
import com.javanauta.usuarios.infrastructure.exceptions.IllegalArgumentException;
import com.javanauta.usuarios.infrastructure.security.client.ViaCepClient;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient viaCepClient;

    public ViaCepDTO buscarDadosEndereco(String cep) {
        try {
            return viaCepClient.buscarDadosEndereco(processarCEP(cep));
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao informar o CEP: " + e.getMessage());
        }
    }

    private String processarCEP(String cep) {
        // Remover traços, pontos e espaços do CEP
        String cepFormatado = cep
                .replace("-", "")
                .replace(" ", "")
                .replace(".", "");

        // Verificar se o CEP contém apenas Números e possui tamanho de 8 caracteres
        if (!cepFormatado.matches("\\d+") || !Objects.equals(cepFormatado.length(), 8)) {
            throw new IllegalArgumentException("CEP inválido! Favor verifificar.");
        }

        return cepFormatado;
    }
}
