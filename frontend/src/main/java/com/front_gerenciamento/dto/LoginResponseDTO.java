package com.front_gerenciamento.dto;

public record LoginResponseDTO (
        String token,
        String tipo,
        long expiraEmSegundos
){

}

