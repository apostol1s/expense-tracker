package gr.aueb.cf.expensetracker.service;

import gr.aueb.cf.expensetracker.DTO.GetUserDTO;
import gr.aueb.cf.expensetracker.model.Expense;
import gr.aueb.cf.expensetracker.model.Role;
import gr.aueb.cf.expensetracker.model.User;
import gr.aueb.cf.expensetracker.repository.ExpenseRepository;
import gr.aueb.cf.expensetracker.repository.UserRepository;
import gr.aueb.cf.expensetracker.service.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    /**
     * Retrieves a list of users with role 'User'.
     *
     * @return  List of GetUserDTO containing basic user information.
     */
    @Override
    public List<GetUserDTO> getUsers() {
        List<User> users = userRepository.findUsersByUserGroupRole(Role.User);
        List<GetUserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            GetUserDTO userDTO = new GetUserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setFirstname(user.getFirstname());
            userDTO.setLastname(user.getLastname());
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    /**
     * Deletes a user and associated expenses by user ID
     *
     * @param userId ID of the user to be deleted.
     */
    @Override
    @Transactional
    public void deleteUserById(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));

        List<Expense> expenses = expenseRepository.findAllByUser(user);
        expenseRepository.deleteAll(expenses);
        user.getUserGroup().getUsers().remove(user);
        userRepository.delete(user);
    }
}
