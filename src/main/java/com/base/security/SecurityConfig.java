package com.base.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
            );
        
        return http.build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return converter;
    }

    /**
     * Custom converter to extract roles from Keycloak JWT tokens.
     * Supports both realm-level and client-level roles.
     */
    static class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
        
        @Override
        public Collection<GrantedAuthority> convert(Jwt jwt) {
            Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
            Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
            
            Stream<String> realmRoles = Stream.empty();
            if (realmAccess != null && realmAccess.containsKey("roles")) {
                @SuppressWarnings("unchecked")
                List<String> roles = (List<String>) realmAccess.get("roles");
                realmRoles = roles.stream();
            }
            

            Stream<String> clientRoles = Stream.empty();
            if (resourceAccess != null) {

                String clientId = jwt.getAudience() != null && !jwt.getAudience().isEmpty()
                    ? jwt.getAudience().getFirst()
                    : null;
                
                if (clientId != null) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get(clientId);
                    if (clientAccess != null && clientAccess.containsKey("roles")) {
                        @SuppressWarnings("unchecked")
                        List<String> roles = (List<String>) clientAccess.get("roles");
                        clientRoles = roles.stream();
                    }
                }
            }
            
            return Stream.concat(realmRoles, clientRoles)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        }
    }
}
