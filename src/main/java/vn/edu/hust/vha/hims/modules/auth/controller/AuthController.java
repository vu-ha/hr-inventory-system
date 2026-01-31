package vn.edu.hust.vha.hims.modules.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.modules.auth.dto.JwtResponse;
import vn.edu.hust.vha.hims.modules.auth.dto.LoginRequest;
import vn.edu.hust.vha.hims.modules.auth.security.JwtUtils;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // 1. Xác thực username và password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // 2. Lưu vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Tạo Token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // 4. Trả về cho Client
        return ResponseEntity.ok(new JwtResponse(jwt, loginRequest.getUsername()));
    }
}