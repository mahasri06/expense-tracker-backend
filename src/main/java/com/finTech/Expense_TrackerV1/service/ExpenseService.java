package com.finTech.Expense_TrackerV1.service;

import com.finTech.Expense_TrackerV1.dto.ExpenseRequest;
import com.finTech.Expense_TrackerV1.dto.ExpenseRequestMapper;
import com.finTech.Expense_TrackerV1.entity.Category;
import com.finTech.Expense_TrackerV1.entity.Expense;
import com.finTech.Expense_TrackerV1.entity.PaymentMethod;
import com.finTech.Expense_TrackerV1.entity.User;
import com.finTech.Expense_TrackerV1.exception.ErrorCode;
import com.finTech.Expense_TrackerV1.exception.ResourceNotFoundException;
import com.finTech.Expense_TrackerV1.repository.CategoryRepository;
import com.finTech.Expense_TrackerV1.repository.ExpenseRepository;
import com.finTech.Expense_TrackerV1.repository.PaymentMethodRepository;
import com.finTech.Expense_TrackerV1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final UserRepository userRepository;
    private final ExpenseRequestMapper expenseRequestMapper;

    public ExpenseService(ExpenseRepository expenseRepository,
                          CategoryRepository categoryRepository,
                          PaymentMethodRepository paymentMethodRepository,
                          UserRepository userRepository,
                          ExpenseRequestMapper expenseRequestMapper){
        this.expenseRepository= expenseRepository;
        this.categoryRepository = categoryRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.userRepository =userRepository;
        this.expenseRequestMapper= expenseRequestMapper;
    }

    public List<Expense> getAllExpenses(Long userId){
        userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "User not found with id: " + userId,
                        ErrorCode.USER_NOT_FOUND
                )
        );
        return expenseRepository.findByUser_Id(userId);
    }

    public Expense getExpenseById(Long id, Long userId){
        return expenseRepository.findByIdAndUser_Id(id, userId).orElseThrow(
                () -> new ResourceNotFoundException("Expense id: " + id + " for the user: " + userId + " not found",
                        ErrorCode.EXPENSE_NOT_FOUND)
        );

    }

    public Expense createExpense(ExpenseRequest expenseRequest, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId,
                        ErrorCode.USER_NOT_FOUND)
        );

        Expense expense = new Expense();

        expenseRequestMapper.mapRequestToExpense(expense, expenseRequest);

        expense.setUser(user);
        Category category = categoryRepository
                .findByIdAndUser_Id(expenseRequest.getCategoryId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category id: " + expenseRequest.getCategoryId() + " not found for this User: " + userId,
                        ErrorCode.CATEGORY_NOT_FOUND)
                );
        expense.setCategory(category);

        PaymentMethod paymentMethod = paymentMethodRepository
                .findByIdAndUser_Id(expenseRequest.getPaymentMethodId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment Method id: " + expenseRequest.getPaymentMethodId() +" not found for this User: " + userId,
                        ErrorCode.PAYMENT_METHOD_NOT_FOUND)
                );
        expense.setPaymentMethod(paymentMethod);

        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id,ExpenseRequest expenseRequest, Long userId){
        Expense existing = expenseRepository.findByIdAndUser_Id(id,userId)
                        .orElseThrow(() ->
                new ResourceNotFoundException(
                        "Expense id: " + id + " not found",
                        ErrorCode.EXPENSE_NOT_FOUND
                ));

        Category category = categoryRepository
                .findByIdAndUser_Id(expenseRequest.getCategoryId(), userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category id: "+ expenseRequest.getCategoryId()+" not found for this user: " + userId,
                                ErrorCode.CATEGORY_NOT_FOUND
                        ));
        PaymentMethod paymentMethod = paymentMethodRepository
                .findByIdAndUser_Id(expenseRequest.getPaymentMethodId(), userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment method id: " + expenseRequest.getPaymentMethodId() +" not found for this user: " + userId,
                                        ErrorCode.PAYMENT_METHOD_NOT_FOUND
                                ));

        expenseRequestMapper.mapRequestToExpense(existing, expenseRequest);
        existing.setCategory(category);
        existing.setPaymentMethod(paymentMethod);

        return expenseRepository.save(existing);
    }

    public void deleteExpense(Long id, Long userId){
        Expense expense = expenseRepository
                .findByIdAndUser_Id(id, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense id: " +id +" not found",
                                ErrorCode.EXPENSE_NOT_FOUND
                        ));

        expenseRepository.delete(expense);
    }
}
