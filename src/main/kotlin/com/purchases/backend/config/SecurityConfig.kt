package com.purchases.backend.config

import com.purchases.backend.identity.TokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.access.channel.ChannelProcessingFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@Order(1)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var tokenUtil: TokenUtil

    @Throws(Exception::class)
    override fun configure(web: WebSecurity?) {
        // Filters will not get executed for the resources
        web!!.ignoring().antMatchers("/", "/resources/**", "/static/**", "/public/**", "/webui/**", "/h2-console/**", "/configuration/**", "/swagger-ui/**", "/swagger-resources/**", "/api-docs", "/api-docs/**", "/v2/api-docs/**", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.gif", "/**/*.svg", "/**/*.ico", "/**/*.ttf", "/**/*.woff", "/**/*.otf")
    }

    //If Security is not working check application.properties if it is set to ignore
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .exceptionHandling().and()
                .anonymous().and()
                // Disable Cross site references
                .csrf().disable()
                // Add CORS Filter
                .addFilterBefore(CorsFilter(), ChannelProcessingFilter::class.java)
                // Custom Token based authentication based on the header previously given to the client
                .addFilterBefore(VerifyTokenFilter(tokenUtil), UsernamePasswordAuthenticationFilter::class.java)
                // custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
                .addFilterBefore(GenerateTokenForUserFilter("/session", authenticationManager(), tokenUtil), UsernamePasswordAuthenticationFilter::class.java)
                .authorizeRequests()
                .antMatchers("/error").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
    }

    /*
    * If You want to store encoded password in your databases and authenticate user
    * based on encoded password then uncomment the below method and provde an encoder

    //@Autowired
    //private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    */
}
