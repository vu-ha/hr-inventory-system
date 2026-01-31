package vn.edu.hust.vha.hims.modules.auth.dto;

import lombok.Data;
import java.util.Set;
import java.util.UUID;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private UUID employeeId;
    private Set<String> roles; // ["ROLE_ADMIN", "ROLE_USER"]
}