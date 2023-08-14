package grandchild.grandchild.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@EnableWebFluxSecurity
@ComponentScan("grandchild.grandchild")
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(authorizeExchange -> authorizeExchange
                        .pathMatchers("/app/member/**").permitAll()
                        .anyExchange().authenticated()
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
                );
        return http.build();
    }
}