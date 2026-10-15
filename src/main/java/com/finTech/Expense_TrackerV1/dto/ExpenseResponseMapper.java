package com.finTech.Expense_TrackerV1.dto;

import com.finTech.Expense_TrackerV1.entity.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseResponseMapper {
    public ExpenseResponse toExpenseResponse(Expense expense){
        ExpenseResponse expenseResponse =new ExpenseResponse();

        expenseResponse.setId(expense.getId());
        expenseResponse.setTitle(expense.getTitle());
        expenseResponse.setAmount(expense.getAmount());
        expenseResponse.setExpenseDate(expense.getExpenseDate());
        expenseResponse.setDescription(expense.getDescription());
        expenseResponse.setCategoryId(expense.getCategory().getId());
        expenseResponse.setPaymentMethodId(expense.getPaymentMethod().getId());
        expenseResponse.setCreatedAt(expense.getCreatedAt());
        expenseResponse.setUpdatedAt(expense.getUpdatedAt());

        return expenseResponse;
    }
}
