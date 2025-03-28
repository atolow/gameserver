package example.com.gameserver.config;


import example.com.gameserver.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .formLogin(login -> login
                        .loginPage("/login")                     // 사용자 정의 로그인 페이지
                        .defaultSuccessUrl("/", true)            // 로그인 성공 시 리다이렉트
                        .failureUrl("/login?error=true")         // 실패 시
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")              // 로그아웃 성공 시
                        .permitAll()
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/balances","/notices" ,"/notices/**","/commands","/commands/**", "/boards","/boards/**",  "/error", "/error/**","/", "/register", "/users/register", "/login", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService);     // 사용자 인증 로직 연결ㄴ

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 해싱
    }

}