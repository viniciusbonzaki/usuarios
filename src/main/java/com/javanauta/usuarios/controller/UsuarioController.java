package com.javanauta.usuarios.controller;

import com.javanauta.usuarios.business.UsuarioService;
import com.javanauta.usuarios.business.dtos.EnderecoDTO;
import com.javanauta.usuarios.business.dtos.TelefoneDTO;
import com.javanauta.usuarios.business.dtos.UsuarioDTO;
import com.javanauta.usuarios.infrastructure.entity.Endereco;
import com.javanauta.usuarios.infrastructure.security.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public String loginUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        // Autenticar o Usuario por Email e Senha através do AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );

        // Gerar e Retornar um Token JWT através do Email (getName) após a Autenticação
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizarDadosUsuario(@RequestBody UsuarioDTO usuarioDTO,
                                                            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(token, usuarioDTO));
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizarDadosEndereco(@RequestParam("id") Long id,
                                                              @RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.ok(usuarioService.atualizarEndereco(id, enderecoDTO));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizarDadosTelefone(@RequestParam("id") Long id,
                                                              @RequestBody TelefoneDTO telefoneDTO) {
        return ResponseEntity.ok(usuarioService.atualizarTelefone(id, telefoneDTO));
    }

    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO> cadastrarEndereco(@RequestHeader("Authorization") String token,
                                                         @RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.ok(usuarioService.cadastrarEndereco(token, enderecoDTO));
    }

    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> cadastrarTelefone(@RequestHeader("Authorization") String token,
                                                         @RequestBody TelefoneDTO telefoneDTO) {
        return ResponseEntity.ok(usuarioService.cadastrarTelefone(token, telefoneDTO));
    }
}
