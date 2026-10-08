package com.front_gerenciamento.cursos_frontend;

import com.front_gerenciamento.auth.TokenSessionService;
import com.front_gerenciamento.controller.CursoWebController;
import com.front_gerenciamento.dto.CursoRequestDTO;
import com.front_gerenciamento.dto.CursoResponseDTO;
import com.front_gerenciamento.service.CursoClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.List;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(CursoWebController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CursoWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CursoClientService cursoClientService;

    @MockitoBean
    private TokenSessionService tokenSessionService;

    @Test
    void deveListarCursos() throws Exception {
        List<CursoResponseDTO> cursosFalso = List.of(
                new CursoResponseDTO(UUID.randomUUID(), "Java", "desc", 40, "Prof A", "Backend")
        );

        when(tokenSessionService.recuperarToken(any())).thenReturn("token-fake");
        when(cursoClientService.lista("token-fake")).thenReturn(cursosFalso);

        mockMvc.perform(get("/cursos"))
                .andExpect(status().isOk())
                .andExpect(view().name("cursos/lista"))
                .andExpect(model().attributeExists("cursos"));
    }

    @Test
    void deveCriarCursoComDadosValidos() throws Exception {
        when(tokenSessionService.recuperarToken(any())).thenReturn("token-fake");

        mockMvc.perform(post("/cursos").param("nome", "Java Básico")
                .param("descricao", "Introdução à linguagem Java")
                .param("professor", "Maria Silva")
                .param("cargaHoraria", "40")
                .param("categoria", "Backend")
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cursos"));

        verify(cursoClientService).criar(any(CursoRequestDTO.class), eq("token-fake"));

    }
    @Test
    void naoDeveCriarCursoComNomeVazio() throws Exception {
        when(tokenSessionService.recuperarToken(any())).thenReturn("token-fake");

        mockMvc.perform(post("/cursos")
                        .param("nome", "")
                        .param("descricao", "Introdução à linguagem Java")
                        .param("professor", "Maria Silva")
                        .param("cargaHoraria", "40")
                        .param("categoria", "Backend")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("cursos/form"))
                .andExpect(model().attributeHasFieldErrors("form", "nome"));

        verify(cursoClientService, never()).criar(any(), any());
    }
    @Test
    void deveExcluirCurso() throws Exception {
        UUID id = UUID.randomUUID();
        when(tokenSessionService.recuperarToken(any())).thenReturn("token-fake");

        mockMvc.perform(post("/cursos/{id}/excluir", id).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cursos"));

        verify(cursoClientService).excluir(id, "token-fake");
    }
    @Test
    void deveExibirDetalhesDoCurso() throws Exception {
        UUID id = UUID.randomUUID();
        CursoResponseDTO cursoFalso = new CursoResponseDTO(id, "Java", "desc", 40, "Prof A", "Backend");

        when(tokenSessionService.recuperarToken(any())).thenReturn("token-fake");
        when(cursoClientService.buscarPorId(id, "token-fake")).thenReturn(cursoFalso);

        mockMvc.perform(get("/cursos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("cursos/detalhe"))
                .andExpect(model().attribute("curso", cursoFalso));
    }
    @Test
    void deveExibirFormularioDeEdicaoPreenchido() throws Exception {
        UUID id = UUID.randomUUID();
        CursoResponseDTO cursoFalso = new CursoResponseDTO(id, "Java", "desc", 40, "Prof A", "Backend");

        when(tokenSessionService.recuperarToken(any())).thenReturn("token-fake");
        when(cursoClientService.buscarPorId(id, "token-fake")).thenReturn(cursoFalso);

        mockMvc.perform(get("/cursos/{id}/editar", id))
                .andExpect(status().isOk())
                .andExpect(view().name("cursos/form"))
                .andExpect(model().attribute("cursoId", id))
                .andExpect(model().attribute("form", hasProperty("nome", equalTo("Java"))));
    }
    @Test
    void deveEditarCursoComDadosValidos() throws Exception {
        UUID id = UUID.randomUUID();
        when(tokenSessionService.recuperarToken(any())).thenReturn("token-fake");

        mockMvc.perform(post("/cursos/{id}/editar", id)
                        .param("nome", "Java Avançado")
                        .param("descricao", "Tópicos avançados")
                        .param("professor", "Maria Silva")
                        .param("cargaHoraria", "60")
                        .param("categoria", "Backend")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cursos/" + id));

        verify(cursoClientService).editar(eq(id), any(CursoRequestDTO.class), eq("token-fake"));
    }
    @Test
    void naoDeveEditarCursoComCargaHorariaInvalida() throws Exception {
        UUID id = UUID.randomUUID();
        when(tokenSessionService.recuperarToken(any())).thenReturn("token-fake");

        mockMvc.perform(post("/cursos/{id}/editar", id)
                        .param("nome", "Java Avançado")
                        .param("descricao", "Tópicos avançados")
                        .param("professor", "Maria Silva")
                        .param("cargaHoraria", "-5")
                        .param("categoria", "Backend")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("cursos/form"))
                .andExpect(model().attributeHasFieldErrors("form", "cargaHoraria"));

        verify(cursoClientService, never()).editar(any(), any(), any());
    }
}
