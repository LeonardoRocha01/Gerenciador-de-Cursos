package com.front_gerenciamento.auth;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class TokenSessionService {

    private static final String TOKEN_ATTRIBUTE = "JWT_TOKEN";
    public void salvarToken(HttpSession session, String token){
        session.setAttribute(TOKEN_ATTRIBUTE, token);
    }

    public String recuperarToken(HttpSession session){
        return (String) session.getAttribute(TOKEN_ATTRIBUTE);
    }

    public void removerToken(HttpSession session){
        session.removeAttribute(TOKEN_ATTRIBUTE);
    }
}
