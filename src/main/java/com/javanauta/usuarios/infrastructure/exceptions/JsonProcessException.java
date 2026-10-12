package com.javanauta.usuarios.infrastructure.exceptions;

public class JsonProcessException extends RuntimeException {
    public JsonProcessException(String mensagem) {
        super(mensagem);
    }

    public JsonProcessException(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }
}
