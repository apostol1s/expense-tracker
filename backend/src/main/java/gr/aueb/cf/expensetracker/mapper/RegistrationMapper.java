package gr.aueb.cf.expensetracker.mapper;

import gr.aueb.cf.expensetracker.DTO.RegistrationDTO;
import gr.aueb.cf.expensetracker.model.Role;
import gr.aueb.cf.expensetracker.model.User;
import gr.aueb.cf.expensetracker.model.UserGroup;
import gr.aueb.cf.expensetracker.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RegistrationMapper {

    private final PasswordEncoder passwordEncoder;
    private final UserGroupRepository userGroupRepository;

    /**
     * Maps a RegistrationDTO to a User entity.
     *
     * @param registrationDTO   the registration data transfer object containing user registration details
     * @return                  the mapped User entity
     */
    public User registrationMapper(RegistrationDTO registrationDTO) {
        UserGroup userGroup = userGroupRepository.findByRole(Role.User);
        User user = new User();
        user.setFirstname(registrationDTO.getFirstname());
        user.setLastname(registrationDTO.getLastname());
        user.setEmail(registrationDTO.getEmail());
        user.setUserGroup(userGroup);
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setLocked(false);
        user.setEnabled(true);
        return user;
    }
}
