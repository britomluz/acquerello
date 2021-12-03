package com.restaurant.acquerello.configurations;




public class WebAuthorization{

   /*@Override
    protected void configure(HttpSecurity http) throws Exception{

        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                    .formLogin()
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginPage("/api/login")
                        .successHandler((req, res, auth) -> clearAuthenticationAttributes(req))
                        .failureHandler((req, res, exc) -> {
                            if (exc.getMessage().contains("Maximum sessions of 2 for this principal exceeded")){
                                res.sendError(HttpServletResponse.SC_CONFLICT,"Ya posee sesión iniciada");
                            } else{
                                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);}
                        })
                .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
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

        if (session !=null){
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }*/
}
