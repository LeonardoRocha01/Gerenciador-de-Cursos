package com.front_gerenciamento.auth;

import com.front_gerenciamento.dto.LoginRequestDTO;
import com.front_gerenciamento.dto.LoginResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AuthClientService {

    private final RestClient restClient;
    public AuthClientService(RestClient restClient) {
        this.restClient = restClient;
    }

    public LoginResponseDTO login(String username, String password) {
        LoginRequestDTO request =  new LoginRequestDTO(username, password);

        return restClient.post()
                .uri("/api/auth/login")
                .body(request)
                .retrieve()
                .body(LoginResponseDTO.class);
    }


}

