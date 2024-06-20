package gr.aueb.cf.expensetracker.controller;

import gr.aueb.cf.expensetracker.DTO.CreateExpenseDTO;
import gr.aueb.cf.expensetracker.DTO.GetAllExpenseDTO;
import gr.aueb.cf.expensetracker.DTO.UpdateExpenseDTO;
import gr.aueb.cf.expensetracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    /**
     * Adds a new expense.
     *
     * @param principal         the user principal
     * @param createExpenseDTO  the DTO containing expense creation data
     * @return                  ResponseEntity with status 201 (Created)
     */
    @Operation(summary = "Add an expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Expense created"),
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
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CreateExpense')")
    public ResponseEntity<Void> createExpense(Principal principal, @RequestBody CreateExpenseDTO createExpenseDTO) {
        expenseService.createExpense(createExpenseDTO, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Retrieves all expenses.
     *
     * @param principal the user principal
     * @return          ResponseEntity with a list of all expenses and status 200 (OK)
     */
    @Operation(summary = "Get all expenses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful return",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAllExpenseDTO.class)) )}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User has not the appropriate authority",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @GetMapping("/overview")
    @PreAuthorize("hasAuthority('GetAllExpenses')")
    public ResponseEntity<List<GetAllExpenseDTO>> getAllExpenses(Principal principal) {
      return ResponseEntity.status(HttpStatus.OK).body(expenseService.getAllExpenses(principal.getName()));
    }

    /**
     * Updates an existing expense.
     *
     * @param principal         the user principal
     * @param expenseId         the ID of the expense to update
     * @param updateExpenseDTO  the DTO containing expense update data
     * @return                  ResponseEntity with status 200 (OK)
     */
    @Operation(summary = "Update an expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense updated"),
            @ApiResponse(responseCode = "400", description = "Invalid expense type or invalid expense id",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User has not the appropriate authority",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User or expense id not found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PutMapping("/update/{expenseId}")
    @PreAuthorize("hasAuthority('UpdateExpenseById')")
    public ResponseEntity<Void> updateExpense(Principal principal,@PathVariable UUID expenseId,@RequestBody UpdateExpenseDTO updateExpenseDTO) {
        expenseService.updateExpense(updateExpenseDTO, expenseId, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Deletes an expense.
     *
     * @param principal the user principal.
     * @param expenseId the ID of the expense to delete
     * @return          ResponseEntity with status 200 (OK).
     */
    @Operation(summary = "Delete an expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid expense id",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User has not the appropriate authority",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User or expense id not found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @DeleteMapping("/delete/{expenseId}")
    @PreAuthorize("hasAuthority('DeleteExpenseById')")
    public ResponseEntity<Void> deleteExpense(Principal principal, @PathVariable UUID expenseId) {
        expenseService.deleteExpense(expenseId, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
