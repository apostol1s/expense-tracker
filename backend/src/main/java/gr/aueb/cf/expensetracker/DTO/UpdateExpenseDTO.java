package gr.aueb.cf.expensetracker.DTO;

import lombok.Data;

@Data
public class UpdateExpenseDTO {

    private String description;
    private String expenseType;
    private double amount;
}
