package vn.edu.hust.vha.hims.modules.auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.modules.auth.entity.UserAccount;
import vn.edu.hust.vha.hims.modules.auth.repository.UserAccountRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Tìm user trong DB theo username
        UserAccount user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với username: " + username));

        // 2. Ép kiểu dữ liệu từ UserAccount sang UserPrincipal (UserDetails) 
        // để Spring Security có thể làm việc được
        return new UserPrincipal(user);
    }
}