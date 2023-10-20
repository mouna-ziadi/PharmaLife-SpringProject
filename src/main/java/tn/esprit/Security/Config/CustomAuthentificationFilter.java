package tn.esprit.Security.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tn.esprit.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.esprit.Services.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
//@RestController
public class CustomAuthentificationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
   private UserRepository userRepository;
    public CustomAuthentificationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/custom-login");

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String email = request.getParameter("username");
        String password = request.getParameter("password");

        // Check if username and password are valid
        // boolean isAuthenticated = performCustomAuthentication(email, password);

        // Check if user exists and if the password matches the encoded password
        if (email.isEmpty()) {
            log.info("Invalid username or password");
            throw new AuthenticationServiceException("Invalid username or password");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }


    /*@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {


        //get info from user to create jwt token
        User user = (User) authentication.getPrincipal();


            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            String access_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000000))
                    .withIssuer(request.getRequestURI().toString())
                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);

            String refresh_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 432_000_000))
                    .withIssuer(request.getRequestURI().toString())
                    .sign(algorithm);


            // response.setHeader("access_token", access_token);
            // response.setHeader("refresh_token", refresh_token);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", access_token);
            tokens.put("refresh_token", refresh_token);
            tokens.put("user", user.getUsername());


            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        }*/


    }

