package com.front_gerenciamento.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalWebExceptionHandler {

    @ExceptionHandler({HttpClientErrorException.Unauthorized.class, HttpClientErrorException.Forbidden.class})
    public String tratarAutenticacaoInvalida(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("erro", "Sua sessão expirou. Faça login novamente.");
        return "redirect:/login";
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public String tratarNaoEncontrado(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("erro", "Curso não encontrado.");
        return "redirect:/cursos";
    }

    @ExceptionHandler(Exception.class)
    public String tratarErro(Model model){
        model.addAttribute("mensagem", "Ocorreu um erro inesperado. Tente novamente.");
        return "erro";
    }
}
