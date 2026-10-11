package com.finTech.Expense_TrackerV1.service;

import com.finTech.Expense_TrackerV1.entity.Expense;
import com.finTech.Expense_TrackerV1.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository){
        this.expenseRepository= expenseRepository;
    }
    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

}
