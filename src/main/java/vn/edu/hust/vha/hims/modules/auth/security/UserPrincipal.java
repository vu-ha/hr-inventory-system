package vn.edu.hust.vha.hims.modules.auth.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import vn.edu.hust.vha.hims.common.enumeration.AccountStatus;
import vn.edu.hust.vha.hims.modules.auth.entity.UserAccount;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private final UserAccount userAccount; // Chứa thông tin Entity bạn vừa tạo thành công

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Đây chính là đoạn code bạn hỏi: Chuyển Enum sang quyền của Spring
        return userAccount.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return userAccount.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Nếu status là ACTIVE thì không bị khóa
        return userAccount.getStatus() == AccountStatus.ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userAccount.getStatus() == AccountStatus.ACTIVE;
    }
}