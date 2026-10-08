package com.front_gerenciamento.controller;

import com.front_gerenciamento.auth.AuthClientService;
import com.front_gerenciamento.auth.TokenSessionService;
import com.front_gerenciamento.dto.CursoRequestDTO;
import com.front_gerenciamento.dto.CursoResponseDTO;
import com.front_gerenciamento.dto.LoginResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Controller
public class AuthController {

    private final AuthClientService authClientService;
    private final TokenSessionService tokenSessionService;

    public AuthController(AuthClientService authClientService, TokenSessionService tokenSessionService) {
        this.authClientService = authClientService;
        this.tokenSessionService = tokenSessionService;
    }

    @GetMapping("/login")
    public String exibirLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String processarLogin(Model model, HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
        try {
            LoginResponseDTO response = authClientService.login(username, password);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            tokenSessionService.salvarToken(session, response.token());

            return "redirect:/cursos";

        }catch (HttpClientErrorException.Unauthorized e) {
            model.addAttribute("erro", "Usuário ou senha inválida");
            return "login";
        }
    }

}
