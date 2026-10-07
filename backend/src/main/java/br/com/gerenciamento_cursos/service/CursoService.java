package br.com.gerenciamento_cursos.service;

import br.com.gerenciamento_cursos.DTO.CursoRequestDTO;
import br.com.gerenciamento_cursos.DTO.CursoResponseDTO;
import br.com.gerenciamento_cursos.entity.CursoEntity;
import br.com.gerenciamento_cursos.exceptions.CursoNotFoundException;
import br.com.gerenciamento_cursos.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<CursoResponseDTO> listarCursos() {
        return cursoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CursoResponseDTO buscarPorId(UUID id) {
        CursoEntity entity = buscarEntidadePorId(id);
        return toResponseDTO(entity);
    }

    public CursoResponseDTO criar(CursoRequestDTO cursoRequestDTO) {
        CursoEntity cursoEntity = new CursoEntity();
        aplicarDados(cursoEntity, cursoRequestDTO);

        CursoEntity salvo = cursoRepository.save(cursoEntity);
        return toResponseDTO(salvo);
    }

    public CursoResponseDTO atualizar(UUID id, CursoRequestDTO cursoRequestDTO) {
        CursoEntity cursoEntity = buscarEntidadePorId(id);
        aplicarDados(cursoEntity, cursoRequestDTO);

        CursoEntity atualizado = cursoRepository.save(cursoEntity);
        return toResponseDTO(atualizado);
    }

    public void excluir(UUID id) {
        CursoEntity cursoEntity = buscarEntidadePorId(id);
        cursoRepository.delete(cursoEntity);
    }

    private CursoEntity buscarEntidadePorId(UUID id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new CursoNotFoundException(id));
    }

    private void aplicarDados(CursoEntity entity, CursoRequestDTO dto) {
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        entity.setCargaHoraria(dto.cargaHoraria());
        entity.setProfessor(dto.professor());
        entity.setCategoria(dto.categoria());
    }

    private CursoResponseDTO toResponseDTO(CursoEntity entity) {
        return new CursoResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getCargaHoraria(),
                entity.getProfessor(),
                entity.getCategoria()
        );
    }
}