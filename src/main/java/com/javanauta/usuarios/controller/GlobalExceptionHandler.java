package com.javanauta.usuarios.controller;

import com.javanauta.usuarios.business.dtos.ErrorResponseDTO;
import com.javanauta.usuarios.infrastructure.exceptions.*;
import com.javanauta.usuarios.infrastructure.exceptions.IllegalArgumentException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Tratar uma ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException e,
                                                                            HttpServletRequest request) {
        ErrorResponseDTO erro = construirMensagemErro(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                "Não Encontrado!",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    // Tratar uma ConflicException
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflictException(ConflictException e,
                                                          HttpServletRequest request) {
        ErrorResponseDTO erro = construirMensagemErro(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                "Recurso já Existente!",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(erro);
    }

    // Tratar uma UnauthorizedException
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorizedException(UnauthorizedException e,
                                                                        HttpServletRequest request) {
        ErrorResponseDTO erro = construirMensagemErro(
                HttpStatus.UNAUTHORIZED.value(),
                e.getMessage(),
                "Não Autorizado/Sem Permissão!",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(erro);
    }

    // Tratar uma IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException e,
                                                                           HttpServletRequest request) {
        ErrorResponseDTO erro = construirMensagemErro(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                "Dados fora do Padrão esperado!",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    // Tratar uma JsonProcessException
    @ExceptionHandler(JsonProcessException.class)
    public ResponseEntity<ErrorResponseDTO> handleJsonProcessException(JsonProcessException e,
                                                                       HttpServletRequest request) {
        ErrorResponseDTO erro = construirMensagemErro(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                "Erro ao tentar processar o JSON!",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    // Método para construir uma Mensagem de Erro utilizando um DTO
    private ErrorResponseDTO construirMensagemErro(int statusHttp, String erro, String mensagem, String caminhoErro) {
        // Criar um Erro utulizando a classe ErrorResponseDTO
        return ErrorResponseDTO.builder()
                // Hora do Erro
                .timestamp(LocalDateTime.now())
                // Status HTTP do Erro
                .httpStatus(statusHttp)
                // Mensagem do Erro gerada pelo Sistema
                .error(erro)
                // Mensagem personalizada do Erro
                .message(mensagem)
                // Caminho do Erro
                .path(caminhoErro)
                .build();
    }
}
