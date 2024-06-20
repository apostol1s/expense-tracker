package gr.aueb.cf.expensetracker.service;

import gr.aueb.cf.expensetracker.DTO.LoginRequestDTO;
import gr.aueb.cf.expensetracker.DTO.LoginResponseDTO;

public interface LoginService {

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}
