package com.javanauta.usuarios.infrastructure.security.client;

import com.javanauta.usuarios.business.dtos.ViaCepDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "via-cep", url = "${via-cep.url}")
public interface ViaCepClient {
    @GetMapping("/ws/{cep}/json/")
    ViaCepDTO buscarDadosEndereco(@PathVariable("cep") String cep);
}
