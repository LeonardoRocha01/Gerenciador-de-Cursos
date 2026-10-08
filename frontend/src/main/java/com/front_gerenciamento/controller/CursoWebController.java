package com.front_gerenciamento.controller;

import com.front_gerenciamento.auth.TokenSessionService;
import com.front_gerenciamento.dto.CursoFormDTO;
import com.front_gerenciamento.dto.CursoRequestDTO;
import com.front_gerenciamento.dto.CursoResponseDTO;
import com.front_gerenciamento.service.CursoClientService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cursos")
public class CursoWebController {

    private final CursoClientService  cursoClientService;
    private final TokenSessionService  tokenSessionService;

    public CursoWebController(CursoClientService cursoClientService, TokenSessionService tokenSessionService) {
        this.cursoClientService = cursoClientService;
        this.tokenSessionService = tokenSessionService;
    }

    @GetMapping
    public String lista(Model model, HttpSession session) {
        String token = tokenSessionService.recuperarToken(session);
        List<CursoResponseDTO> cursos = cursoClientService.lista(token);

        model.addAttribute("cursos", cursos);
        return "cursos/lista";

    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable UUID id, Model model, HttpSession session) {
        String token = tokenSessionService.recuperarToken(session);
        CursoResponseDTO curso = cursoClientService.buscarPorId(id, token);

        model.addAttribute("curso", curso);
        return "cursos/detalhe";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable UUID id, HttpSession session) {
        String token = tokenSessionService.recuperarToken(session);
        cursoClientService.excluir(id, token);
        return "redirect:/cursos";
    }

    @GetMapping("/novo")
    public String exibirFormularioNovo(Model model){
        model.addAttribute("form", new CursoFormDTO());
        return "cursos/form";
    }

    @PostMapping
    public String criar(@ModelAttribute("form") @Valid CursoFormDTO form, BindingResult bindingResult,
                        HttpSession session) {

        if  (bindingResult.hasErrors()) {
            return "cursos/form";
        }

        String token = tokenSessionService.recuperarToken(session);
        CursoRequestDTO dto = new CursoRequestDTO(form.getNome(), form.getDescricao(), form.getCargaHoraria(),
                form.getProfessor(), form.getCategoria());
        cursoClientService.criar(dto, token);

        return "redirect:/cursos";
    }

    @GetMapping("/{id}/editar")
    public String exibirFormularioEditar(@PathVariable UUID id, Model model, HttpSession session) {
        String token = tokenSessionService.recuperarToken(session);
        CursoResponseDTO curso = cursoClientService.buscarPorId(id, token);

        CursoFormDTO form = new CursoFormDTO();
        form.setNome(curso.nome());
        form.setDescricao(curso.descricao());
        form.setProfessor(curso.professor());
        form.setCargaHoraria(curso.cargaHoraria());
        form.setCategoria(curso.categoria());

        model.addAttribute("form", form);
        model.addAttribute("cursoId", id);

        return "cursos/form";
    }

    @PostMapping("/{id}/editar")
    public String editar(@PathVariable UUID id, @ModelAttribute("form") @Valid CursoFormDTO form,
                         BindingResult bindingResult,
                         Model model,
                         HttpSession session) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("cursoId", id);
            return "cursos/form";
        }

        String token = tokenSessionService.recuperarToken(session);
        CursoRequestDTO dto = new CursoRequestDTO(form.getNome(), form.getDescricao(), form.getCargaHoraria(),
                form.getProfessor(), form.getCategoria());
        cursoClientService.editar(id, dto, token);


        return "redirect:/cursos/{id}";
    }


}
