package com.restaurant.acquerello.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/products", "/api/categories", "/api/cards").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/create", "/api/order/checkout", "/api/order/buy").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                .antMatchers( "/web/index.html", "/web/login.html", "/web/booking.html", "/web/checkout.html", "/scripts/**", "/assets/**","/styles/**").permitAll()
                .antMatchers("/api/**","/web/myaccount.html").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/rest/**", "/h2-console/**", "/web/admin.html", "/web/products.html", "/web/product-details.html", "/web/order-details.html").hasAuthority("ADMIN")
                .and()
                .formLogin()
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginPage("/api/login")
                    .successHandler((req, res, auth) -> clearAuthenticationAttributes(req))
                    .failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .and()
                .sessionManagement()
                    .invalidSessionUrl("/web/login.html")
                    .maximumSessions(2)
                    .maxSessionsPreventsLogin(true)
                    .sessionRegistry(sessionRegistry());

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendRedirect("/web/login.html"));
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }
}
