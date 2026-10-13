package com.finTech.Expense_TrackerV1.controller;

import com.finTech.Expense_TrackerV1.dto.ExpenseRequest;
import com.finTech.Expense_TrackerV1.dto.ExpenseResponse;
import com.finTech.Expense_TrackerV1.entity.Expense;
import com.finTech.Expense_TrackerV1.exception.ApiResponse;
import com.finTech.Expense_TrackerV1.exception.ResourceNotFoundException;
import com.finTech.Expense_TrackerV1.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<ApiResponse> getExpenseByUser_Id(
            @RequestParam Long userId
    ){
        List<Expense> expenses = expenseService.getAllExpenses(userId);
        List<ExpenseResponse> responses = new ArrayList<>();

        for(Expense expense : expenses){
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

            responses.add(expenseResponse);
        }
        return ResponseEntity.ok(
                        new ApiResponse(
                                true, "Expense Retrieved Successfully", responses
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getExpenseById(
            @PathVariable("id") Long id, @RequestParam Long userId
    ){
        Expense expense = expenseService.getExpenseById(id, userId).orElseThrow(() ->
                new ResourceNotFoundException("Expense id: "+ id + " not found"));

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

        return ResponseEntity.ok(
                new ApiResponse(
                        true, "Expense of id: " + id + " retrieved successfully", expenseResponse
                )
        );
    }
}
