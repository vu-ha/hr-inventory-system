package vn.edu.hust.vha.hims.modules.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.hust.vha.hims.common.enumeration.AccountStatus;
import vn.edu.hust.vha.hims.common.enumeration.RoleType;
import vn.edu.hust.vha.hims.modules.auth.dto.RegisterRequest;
import vn.edu.hust.vha.hims.modules.auth.entity.UserAccount;
import vn.edu.hust.vha.hims.modules.auth.repository.UserAccountRepository;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAccountRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return "Lỗi: Username đã tồn tại!";
        }

        UserAccount user = new UserAccount();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(AccountStatus.ACTIVE);
        
        // Chuyển Set<String> từ Request sang Set<RoleType> của Entity
        if (request.getRoles() != null) {
            user.setRoles(request.getRoles().stream()
                .map(RoleType::valueOf)
                .collect(Collectors.toSet()));
        }

        userRepository.save(user);
        return "Đăng ký người dùng thành công!";
    }
}