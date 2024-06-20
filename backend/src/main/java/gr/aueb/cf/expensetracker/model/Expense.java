package gr.aueb.cf.expensetracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "expense")
public class Expense extends AbstractEntity{

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "descreption", columnDefinition = "varchar(255)")
    private String description;

    @Column(name = "expense_type")
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    @Column(name = "amount")
    private double amount;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User user;
}
