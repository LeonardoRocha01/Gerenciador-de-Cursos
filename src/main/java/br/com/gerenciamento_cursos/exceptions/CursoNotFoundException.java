package br.com.gerenciamento_cursos.exceptions;

import java.util.UUID;

public class CursoNotFoundException extends RuntimeException {
    public CursoNotFoundException(UUID id) {
        super("Curso não encontrado com o id: " + id);
    }
}
