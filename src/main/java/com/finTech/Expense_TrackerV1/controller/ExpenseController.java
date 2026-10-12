package com.finTech.Expense_TrackerV1.controller;

import com.finTech.Expense_TrackerV1.dto.ExpenseRequest;
import com.finTech.Expense_TrackerV1.dto.ExpenseResponse;
import com.finTech.Expense_TrackerV1.entity.Expense;
import com.finTech.Expense_TrackerV1.exception.ApiResponse;
import com.finTech.Expense_TrackerV1.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService){
        this.expenseService =expenseService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createExpense(@Valid @RequestBody ExpenseRequest expenseRequest,
                                                     @RequestParam Long userId){
        Expense expense = expenseService.createExpense(expenseRequest, userId);

        ExpenseResponse expenseResponse = new ExpenseResponse();

        expenseResponse.setId(expense.getId());
        expenseResponse.setTitle(expense.getTitle());
        expenseResponse.setAmount(expense.getAmount());
        expenseResponse.setExpenseDate(expense.getExpenseDate());
        expenseResponse.setDescription(expense.getDescription());
        expenseResponse.setCategoryId(expense.getCategory().getId());
        expenseResponse.setPaymentMethodId(expense.getPaymentMethod().getId());
        expenseResponse.setCreatedAt(expense.getCreatedAt());
        expenseResponse.setUpdatedAt(expense.getUpdatedAt());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse(true, "Expense Created Successfully", expenseResponse)
                );
    }
}
