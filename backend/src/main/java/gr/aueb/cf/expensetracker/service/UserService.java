package gr.aueb.cf.expensetracker.service;

import gr.aueb.cf.expensetracker.DTO.GetUserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<GetUserDTO> getUsers();
    void deleteUserById(UUID userId);
}
