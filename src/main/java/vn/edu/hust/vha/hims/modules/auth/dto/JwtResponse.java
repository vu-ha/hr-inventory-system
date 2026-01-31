package vn.edu.hust.vha.hims.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String username;
    private String type = "Bearer";

    public JwtResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }
}