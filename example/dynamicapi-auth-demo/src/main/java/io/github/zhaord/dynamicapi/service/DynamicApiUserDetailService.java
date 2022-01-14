package io.github.zhaord.dynamicapi.service;

import io.github.zhaord.dynamicapi.core.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author zhaord
 */
@Service
public class DynamicApiUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = UserPrincipal.builder()
                .username("admin")
                .password("$2a$10$ST9novnaqfGH8KIBiccUYuSedAu4gFZj9y.DmGlt7VRB80snsRtnu")
                .build();
        return user;
    }
}
