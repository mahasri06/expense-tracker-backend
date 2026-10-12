package com.finTech.Expense_TrackerV1.service;

import com.finTech.Expense_TrackerV1.dto.ExpenseRequest;
import com.finTech.Expense_TrackerV1.entity.Category;
import com.finTech.Expense_TrackerV1.entity.Expense;
import com.finTech.Expense_TrackerV1.entity.PaymentMethod;
import com.finTech.Expense_TrackerV1.entity.User;
import com.finTech.Expense_TrackerV1.exception.ResourceNotFoundException;
import com.finTech.Expense_TrackerV1.repository.CategoryRepository;
import com.finTech.Expense_TrackerV1.repository.ExpenseRepository;
import com.finTech.Expense_TrackerV1.repository.PaymentMethodRepository;
import com.finTech.Expense_TrackerV1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          CategoryRepository categoryRepository,
                          PaymentMethodRepository paymentMethodRepository,
                          UserRepository userRepository){
        this.expenseRepository= expenseRepository;
        this.categoryRepository = categoryRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.userRepository =userRepository;
    }

    public List<Expense> getAllExpenses(Long userId){
        return expenseRepository.findByUser_Id(userId);
    }

    public Optional<Expense> getExpenseById(Long id, Long userId){
        return expenseRepository.findByIdAndUser_Id(id, userId);
    }

    public Expense createExpense(ExpenseRequest expenseRequest, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId)
        );

        Expense expense = new Expense();
        expense.setTitle(expenseRequest.getTitle());
        expense.setAmount(expenseRequest.getAmount());
        expense.setExpenseDate(expenseRequest.getExpenseDate());
        expense.setDescription(expenseRequest.getDescription());

        expense.setUser(user);
        Category category = categoryRepository
                .findByIdAndUser_Id(expenseRequest.getCategoryId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found for this User: " + expenseRequest.getCategoryId())
                );
        expense.setCategory(category);

        PaymentMethod paymentMethod = paymentMethodRepository
                .findByIdAndUser_Id(expenseRequest.getPaymentMethodId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment Method not found for this User: " + expenseRequest.getPaymentMethodId())
                );
        expense.setPaymentMethod(paymentMethod);

        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Expense expense, Long userId){
        Expense existing = expenseRepository.findByIdAndUser_Id(expense.getId(),userId)
                        .orElseThrow();

        Category category = categoryRepository
                .findByIdAndUser_Id(expense.getCategory().getId(), userId)
                .orElseThrow();
        PaymentMethod paymentMethod = paymentMethodRepository
                .findByIdAndUser_Id(expense.getPaymentMethod().getId(), userId)
                        .orElseThrow();

        existing.setTitle(expense.getTitle());
        existing.setAmount(expense.getAmount());
        existing.setExpenseDate(expense.getExpenseDate());
        existing.setDescription(expense.getDescription());
        existing.setCategory(category);
        existing.setPaymentMethod(paymentMethod);

        return expenseRepository.save(existing);
    }

    public void deleteExpense(Long id, Long userId){
        Expense expense = expenseRepository
                .findByIdAndUser_Id(id, userId)
                .orElseThrow();

        expenseRepository.delete(expense);
    }
}
