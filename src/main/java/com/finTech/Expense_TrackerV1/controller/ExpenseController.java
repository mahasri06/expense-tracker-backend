package com.finTech.Expense_TrackerV1.controller;

import com.finTech.Expense_TrackerV1.dto.ExpenseDto.ExpenseRequest;
import com.finTech.Expense_TrackerV1.dto.ExpenseDto.ExpenseResponse;
import com.finTech.Expense_TrackerV1.dto.ExpenseDto.ExpenseResponseMapper;
import com.finTech.Expense_TrackerV1.entity.Expense;
import com.finTech.Expense_TrackerV1.exception.ApiResponse;
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
    private final ExpenseResponseMapper expenseResponseMapper;

    public ExpenseController(ExpenseService expenseService,
                             ExpenseResponseMapper expenseResponseMapper){
        this.expenseService =expenseService;
        this.expenseResponseMapper=expenseResponseMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createExpense(@Valid @RequestBody ExpenseRequest expenseRequest,
                                                     @RequestParam Long userId){
        Expense expense = expenseService.createExpense(expenseRequest, userId);

        ExpenseResponse expenseResponse = expenseResponseMapper.toExpenseResponse(expense);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse(true, "Expense Created Successfully",null, expenseResponse)
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getExpenseByUser_Id(
            @RequestParam Long userId
    ){
        List<Expense> expenses = expenseService.getAllExpenses(userId);
        List<ExpenseResponse> responses = new ArrayList<>();

        for(Expense expense : expenses){
            ExpenseResponse expenseResponse = expenseResponseMapper.toExpenseResponse(expense);

            responses.add(expenseResponse);
        }
        return ResponseEntity.ok(
                new ApiResponse(
                        true, "Expense Retrieved Successfully",null, responses
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getExpenseById(
            @PathVariable("id") Long id, @RequestParam Long userId
    ){
        Expense expense = expenseService.getExpenseById(id, userId);

        ExpenseResponse expenseResponse = expenseResponseMapper.toExpenseResponse(expense);

        return ResponseEntity.ok(
                new ApiResponse(
                        true, "Expense of id: " + id + " retrieved successfully",null,  expenseResponse
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest expenseRequest,
            @RequestParam Long userId
    ){
        Expense expense = expenseService.updateExpense(id,expenseRequest, userId);
        ExpenseResponse expenseResponse = expenseResponseMapper.toExpenseResponse(expense);


        return ResponseEntity.ok(
                new ApiResponse(true, "Expense Updated Successfully",null , expenseResponse)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteExpense(
            @PathVariable Long id,
            @RequestParam Long userId
    ){
        expenseService.deleteExpense(id, userId);

        return ResponseEntity.ok(
                new ApiResponse(
                        true, "Expense id: " + id + " deleted successfully",
                        null,
                        null
                )
        );
    }
}