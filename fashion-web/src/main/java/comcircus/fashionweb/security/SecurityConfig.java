package comcircus.fashionweb.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    } 

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
            .antMatchers("/home", "/", "/shop", "/category", "/contact", "/register", "/login").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .and()
            .formLogin()
                .loginPage("/admin-login")
                .loginProcessingUrl("/admin/admin-login")
                .defaultSuccessUrl("/admin/dashboard")
            // .and()
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .deleteCookies("JSESSIONID")
            .and()
            .exceptionHandling().accessDeniedPage("/forbidden_exception")
            .and()
            .httpBasic();

            //     .authorizeRequests()
            //     .antMatchers("/admin/**").hasRole("ADMIN")
            //     .antMatchers("/**").hasRole("USER")
			// 	.antMatchers("/**").permitAll()
            //     .and()
            //     .formLogin().loginPage("/signin").loginProcessingUrl("/login")
			// 	.defaultSuccessUrl("/user/").and().csrf().disable();

            return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(UserDetailsService userDetailsService) throws Exception {
        return new ProviderManager(getDaoAuthProvider());
    }

    @Bean
	public DaoAuthenticationProvider getDaoAuthProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;
	}

    @Bean
    public BCryptPasswordEncoder  passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler("/home");
    }

    private static class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

        private final String targetUrl;

        public CustomLogoutSuccessHandler(String targetUrl) {
            this.targetUrl = targetUrl;
        }

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.sendRedirect(targetUrl);
        }


    }
}
