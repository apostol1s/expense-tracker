package gr.aueb.cf.expensetracker.DTO;

import gr.aueb.cf.expensetracker.model.ExpenseType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetAllExpenseDTO {
    private UUID id;
    private String description;
    private String expenseType;
    private double amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
