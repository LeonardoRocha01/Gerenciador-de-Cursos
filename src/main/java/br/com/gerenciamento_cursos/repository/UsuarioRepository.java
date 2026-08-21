package br.com.gerenciamento_cursos.repository;

import br.com.gerenciamento_cursos.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {

        Optional<UsuarioEntity> findByUsername(String username);
}
