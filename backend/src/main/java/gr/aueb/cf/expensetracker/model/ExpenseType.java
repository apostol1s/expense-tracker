package gr.aueb.cf.expensetracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExpenseType {
    ELECTRICITY("Electricity"),
    WATER("Water"),
    RENT("Rent"),
    SUPER_MARKET("Super Market"),
    DELIVERY("Delivery");

    private final String code;
}
