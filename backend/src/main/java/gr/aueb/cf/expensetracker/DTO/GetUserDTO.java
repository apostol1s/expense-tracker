package gr.aueb.cf.expensetracker.DTO;


import lombok.Data;

import java.util.UUID;

@Data
public class GetUserDTO {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
}
