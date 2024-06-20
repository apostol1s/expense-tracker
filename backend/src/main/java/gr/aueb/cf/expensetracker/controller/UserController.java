package gr.aueb.cf.expensetracker.controller;

import gr.aueb.cf.expensetracker.DTO.GetUserDTO;
import gr.aueb.cf.expensetracker.DTO.LoginRequestDTO;
import gr.aueb.cf.expensetracker.DTO.LoginResponseDTO;
import gr.aueb.cf.expensetracker.DTO.RegistrationDTO;
import gr.aueb.cf.expensetracker.repository.UserRepository;
import gr.aueb.cf.expensetracker.service.LoginService;
import gr.aueb.cf.expensetracker.service.RegistrationService;
import gr.aueb.cf.expensetracker.service.UserService;
import gr.aueb.cf.expensetracker.service.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final UserService userService;

    /**
     * Registers a new user.
     *
     * @param registrationDTO   the DTO containing registration data
     * @return                  ResponseEntity with status 201 (Created)
     */
    @Operation(summary = "User registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered"),
            @ApiResponse(responseCode = "400", description = "Invalid email pattern or duplicated email",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomException.class))}),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomException.class))})})
    @PostMapping(value = "/register")
    public ResponseEntity<Void> registration(@RequestBody RegistrationDTO registrationDTO) {
        registrationService.registration(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Logs in a user.
     *
     * @param loginRequestDTO   the DTO containing login data
     * @return                  the response DTO containing user login information
     */
    @Operation(summary = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PostMapping(value = "/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return loginService.login(loginRequestDTO);
    }

    /**
     * Retrieves all users.
     *
     * @return ResponseEntity with a list of all users and status 200 (OK)
     */
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User has not the appropriate authority",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('GetAllUsers')")
    public ResponseEntity<List<GetUserDTO>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id    the ID of the user to delete
     * @return      ResponseEntity with status 200 (OK)
     */
    @Operation(summary = "Delete user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid expense type",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User has not the appropriate authority",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DeleteUserById')")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID id){
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
