package gr.aueb.cf.expensetracker.service;

import gr.aueb.cf.expensetracker.DTO.RegistrationDTO;
import gr.aueb.cf.expensetracker.mapper.RegistrationMapper;
import gr.aueb.cf.expensetracker.model.User;
import gr.aueb.cf.expensetracker.repository.UserRepository;
import gr.aueb.cf.expensetracker.service.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final RegistrationMapper registrationMapper;

    /**
     * Registers a new user with the provided registration details.
     *
     * @param registrationdto RegistrationDTO containing user registration details.
     */
    @Override
    public void registration(RegistrationDTO registrationdto) {
        userRepository.findUserByEmail(registrationdto.getEmail()).ifPresent((u) ->
        {throw new CustomException(HttpStatus.BAD_REQUEST, "Email already exists");});
        User user = registrationMapper.registrationMapper(registrationdto);
        userRepository.save(user);
    }
}
