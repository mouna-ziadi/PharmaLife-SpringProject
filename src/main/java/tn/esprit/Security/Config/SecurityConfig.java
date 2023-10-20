package tn.esprit.Security.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
/*@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        ///EMNA
        http.authorizeRequests().antMatchers("/login/**","/registration/**").permitAll();
        //http.authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/User/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/User/**").hasAnyAuthority("Admin","Patient");
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/User/update-User").hasAnyAuthority("Patient");
        http.authorizeRequests().antMatchers(HttpMethod.GET ,"/DeliveryPerson/**").hasAnyAuthority("Admin");


        ////SAHAR
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/Category/add-category").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/Category/***").hasAnyAuthority("Admin","Patient");
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/Category/***").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/Product/***").hasAnyAuthority("Admin","Patient");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/Product/***").hasAnyAuthority("Patient");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/Product/***").hasAnyAuthority("Patient");

       // http.authorizeRequests().antMatchers(HttpMethod.GET,"/Gift/***").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/Gift/***").hasAnyAuthority("Admin","Patient");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/Reclamation/***").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/Reclamation/***").hasAnyAuthority("Patient");


       ///MOUNA
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/associations/***").hasAnyAuthority("Admin","Patient","Donator","AssociationMember");
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/donations/***").hasAnyAuthority("Admin","Patient","Donator","AssociationMember");
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/requests/***").hasAnyAuthority("Admin","Patient","Donator","AssociationMember");

        //Badis
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/reservations/***").hasAnyAuthority("Admin","Patient");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/reservations/***").hasAnyAuthority("Patient","Admin");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/events/***").hasAnyAuthority("Admin","Patient");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/events/***").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/events/***").hasAnyAuthority("Admin");

        ///Elyesss
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/Article/***").hasAnyAuthority("Admin","Patient","Donator","AssociationMember","DrugsProvider");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/Article/***").hasAnyAuthority("Admin","Patient","Donator","AssociationMember","DrugsProvider");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/Comment/***").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/Comment/***").hasAnyAuthority("Admin");

        http.authorizeRequests().antMatchers(HttpMethod.POST,"/Comment/***").hasAnyAuthority("Patient","Donator","AssociationMember","DrugsProvider");


        //Fakher
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/commands/***").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/commands/***").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/commands/***").hasAnyAuthority("Admin");

        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/purchases/***").hasAnyAuthority("Admin");


        http.authorizeRequests().antMatchers(HttpMethod.POST,"/commands/***").hasAnyAuthority("Patient","Donator","AssociationMember","DrugsProvider");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/purchases/***").hasAnyAuthority("Patient","Donator","AssociationMember","DrugsProvider");




        //http.authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthentificationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        // Configure CORS
        http.cors().configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
            corsConfiguration.setAllowedMethods(Arrays.asList("*"));
            corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
            return corsConfiguration;
        });
    }


     @Bean
     public AuthenticationManager authenticationManagerBean() throws  Exception{
       return super.authenticationManagerBean();

     }



}