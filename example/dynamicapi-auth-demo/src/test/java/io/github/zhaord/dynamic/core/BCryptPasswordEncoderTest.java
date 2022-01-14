package io.github.zhaord.dynamic.core;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author zhaord
 */
public class BCryptPasswordEncoderTest {

    @Test
    public void test(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String admin = encoder.encode("admin");
        assertThat(admin).isEqualTo("$2a$10$ST9novnaqfGH8KIBiccUYuSedAu4gFZj9y.DmGlt7VRB80snsRtnu");
    }
}
