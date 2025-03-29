package example.com.gameserver.config;

import example.com.gameserver.session.security.CustomLogoutSuccessHandler;
import example.com.gameserver.session.service.UserSessionService;
import example.com.gameserver.security.CustomUserDetailsService; // CustomLogoutSuccessHandler를 import
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final UserSessionService userSessionService;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;  // CustomLogoutSuccessHandler를 주입

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .formLogin(login -> login
                        .loginPage("/login")                     // 사용자 정의 로그인 페이지
                        .defaultSuccessUrl("/", true)            // 로그인 성공 시 리다이렉트
                        .failureUrl("/login?error=true")         // 실패 시
                        .permitAll()
                        .successHandler(authenticationSuccessHandler())  // 로그인 성공 시 처리
                )
                .logout(logout -> logout
                        .logoutSuccessHandler(customLogoutSuccessHandler) // 로그아웃 성공 시 CustomLogoutSuccessHandler 호출
                        .permitAll()
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/balances","/notices" ,"/notices/**","/commands","/commands/**", "/boards","/boards/**", "/error", "/error/**","/", "/register", "/users/register", "/login", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService);     // 사용자 인증 로직 연결

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 해싱
    }

    // 로그인 성공 시 호출되는 메서드
    private AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // 로그인한 사용자 정보 및 IP 가져오기
            String username = authentication.getName();
            String ipAddress = request.getRemoteAddr();
            LocalDateTime loginTime = LocalDateTime.now();

            // 사용자 세션 업데이트 (IP와 로그인 시간 기록)
            userSessionService.updateUserSession(username, ipAddress, loginTime);
            // 기본 로그인 성공 후 처리
            response.sendRedirect("/");  // 기본 리다이렉트 경로
        };
    }
}