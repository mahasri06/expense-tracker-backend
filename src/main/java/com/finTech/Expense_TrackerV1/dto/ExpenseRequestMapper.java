package com.finTech.Expense_TrackerV1.dto;

import com.finTech.Expense_TrackerV1.entity.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseRequestMapper {
    public void mapRequestToExpense(
            Expense expense,
            ExpenseRequest request) {
        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setDescription(request.getDescription());
    }
}
