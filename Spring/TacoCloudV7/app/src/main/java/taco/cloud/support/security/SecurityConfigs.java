package taco.cloud.support.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import taco.cloud.models.UserData;
import taco.cloud.repositories.UserRepository;

@Configuration
public class SecurityConfigs {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        List<UserDetails> usersList = new ArrayList<>();
//
//        usersList.add(new User("buzz", encoder.encode("password"), null, null, null, null, null, null));
//        usersList.add(new User("woody", encoder.encode("password"), null, null, null, null, null, null));
//
//        return new InMemoryUserDetailsManager(usersList);
//    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            UserData user = userRepository.findByUsername(username);

            if (user != null)
                return user;

            throw new UsernameNotFoundException("User ‘" + username + "’ not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> authz.requestMatchers("/design", "/orders").hasRole("USER")
                        .requestMatchers("/", "/h2-console/**", "/register", "/login", "/images/**").permitAll() // Разрешить
                                                                                                                 // доступ
                                                                                                                 // к H2
                                                                                                                 // Console
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin.loginPage("/login").defaultSuccessUrl("/design", true))
                //.csrf(csrf -> csrf.disable()) // Отключить CSRF
                // для H2 Console
                .headers(headers -> headers.frameOptions().sameOrigin() // Разрешить iframe для H2 Console
                ).build();
    }

}
