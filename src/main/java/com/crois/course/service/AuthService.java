package com.crois.course.service;


import com.crois.course.dto.UserDTO.Login.UserLoginRequestDTO;
import com.crois.course.dto.UserDTO.Login.UserLoginResponse;
import com.crois.course.dto.UserDTO.Response;
import com.crois.course.mapper.UserRegistrationMapper;
import com.crois.course.repositories.UserRepository;
import com.crois.course.service.jwt.JwtAuthenticationEntryPoint;
import com.crois.course.service.jwt.JwtFilter;
import com.crois.course.service.jwt.JwtUserDetailsService;
import com.crois.course.service.jwt.TokenManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@Service
public class AuthService {

    private final JwtUserDetailsService userDetailsService;
    private final TokenManager tokenManager;
    private final AuthenticationManager authenticationManager;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtUserDetailsService userDetailsService,
                       TokenManager tokenManager){

        this.tokenManager = tokenManager;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    public Response createToken(@RequestBody UserLoginRequestDTO request) throws Exception {
        try {
            // Используем authManager, который Spring подставляет в контроллер
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        }
        //исключение, если введенные данные неверны
        catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        //исключение, если аккаунт отключен
        catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        }

        //берем данные пользователя, чтобы занести их в токен
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        final String jwtToken = tokenManager.generateJwtToken(userDetails);

        return Response.ok(new UserLoginResponse(jwtToken));
    }

}
