package gr.aueb.cf.expensetracker.service;

import gr.aueb.cf.expensetracker.DTO.LoginRequestDTO;
import gr.aueb.cf.expensetracker.DTO.LoginResponseDTO;
import gr.aueb.cf.expensetracker.model.User;
import gr.aueb.cf.expensetracker.repository.UserRepository;
import gr.aueb.cf.expensetracker.service.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService{

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Authenticates a user based on the provided credentials and
     * generates a JWT token upon successful authentication.
     *
     * @param loginRequestDTO   login request containing user credentials (email and password).
     * @return                  loginResponseDTO containing JWT token and user role upon successful authentication.
     */
    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findUserByEmail(loginRequestDTO.getEmail()).orElseThrow(
            () -> new CustomException(HttpStatus.UNAUTHORIZED, "User unauthorized"));

        if (!user.isEnabled()) {
            throw new CustomException(HttpStatus.FORBIDDEN, "User is not enabled");
        }

        if (user.isLocked()) {
            throw new CustomException(HttpStatus.LOCKED, "User is locked");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        }catch (BadCredentialsException ex){
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtService.createToken(user);
        return new LoginResponseDTO(token, user.getUserGroup().getRole().toString());
    }
}
