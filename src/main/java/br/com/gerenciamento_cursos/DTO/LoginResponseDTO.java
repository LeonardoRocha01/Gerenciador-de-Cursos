package br.com.gerenciamento_cursos.DTO;

public record LoginResponseDTO (
        String token,
        String tipo,
        long expiraEmSegundos
){
    public  static LoginResponseDTO of(String token, long expirationMs){
        return new LoginResponseDTO(token, "Bearer", expirationMs/1000);
    }
}
