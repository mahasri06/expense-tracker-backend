package com.finTech.Expense_TrackerV1.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExpenseResponse {
    private Long id;

    private String title;

    private BigDecimal amount;

    private LocalDate expenseDate;

    private String description;

    private Long categoryId;

    private Long paymentMethodId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
