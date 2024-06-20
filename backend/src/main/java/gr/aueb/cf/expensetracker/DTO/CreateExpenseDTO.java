package gr.aueb.cf.expensetracker.DTO;

import lombok.Data;

@Data
public class CreateExpenseDTO {

    private String description;
    private String expenseType;
    private double amount;
}
