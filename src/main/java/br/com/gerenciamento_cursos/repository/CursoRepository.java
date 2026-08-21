package br.com.gerenciamento_cursos.repository;

import br.com.gerenciamento_cursos.entity.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CursoRepository extends JpaRepository<CursoEntity, UUID> {
}