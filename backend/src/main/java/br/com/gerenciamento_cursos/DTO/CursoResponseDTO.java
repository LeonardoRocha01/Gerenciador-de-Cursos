package br.com.gerenciamento_cursos.DTO;

import java.util.UUID;

public record CursoResponseDTO( UUID id,
         String nome,
         String descricao,
         Integer cargaHoraria,
         String professor,
         String categoria) {
}