package com.finTech.Expense_TrackerV1.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ExpenseRequest {
    @NotBlank(message = "Title must not be blank")
    @Size(max=100, message = "Title must not exceed 100 characters")
    private String title;

    @NotNull(message = "Amount must not be null")
    @DecimalMin(value = "0.01",message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Expense Date must not be null")
    private LocalDate expenseDate;

    @Size(max = 200, message = "description must not exceed 200 characters")
    private String description;

    @NotNull(message = "Category Id must not be null")
    private Long categoryId;

    @NotNull(message = "Payment Method Id must not be null")
    private Long paymentMethodId;
}
