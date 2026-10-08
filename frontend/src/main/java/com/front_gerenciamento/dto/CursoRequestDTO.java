package com.front_gerenciamento.dto;

public record CursoRequestDTO(
    String nome,

    String descricao,

    Integer cargaHoraria,

    String professor,

    String categoria)
    {
}
