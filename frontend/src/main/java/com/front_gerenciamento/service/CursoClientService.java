package com.front_gerenciamento.service;

import com.front_gerenciamento.dto.CursoRequestDTO;
import com.front_gerenciamento.dto.CursoResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Service
public class CursoClientService {

    private final RestClient restClient;

    public CursoClientService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<CursoResponseDTO> lista(String token) {
        return restClient.get()
                .uri("/api/cursos")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(new ParameterizedTypeReference<List<CursoResponseDTO>>() {});

    }

    public CursoResponseDTO buscarPorId(UUID id, String token) {
        return restClient.get()
                .uri("/api/cursos/{id}", id)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(CursoResponseDTO.class);

    }
    public CursoResponseDTO criar(CursoRequestDTO dto, String token) {
        return restClient.post()
                .uri("/api/cursos")
                .header("Authorization", "Bearer " + token)
                .body(dto)
                .retrieve()
                .body(CursoResponseDTO.class);
    }

    public CursoResponseDTO editar(UUID id,CursoRequestDTO dto, String token) {
        return restClient.put()
                .uri("/api/cursos/{id}", id)
                .header("Authorization", "Bearer " + token)
                .body(dto)
                .retrieve()
                .body(CursoResponseDTO.class);
    }
    public void excluir(UUID id, String token) {
        restClient.delete()
                .uri("/api/cursos/{id}", id)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity();
    }
}
