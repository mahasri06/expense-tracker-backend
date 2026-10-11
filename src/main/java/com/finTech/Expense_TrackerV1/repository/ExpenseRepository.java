package com.finTech.Expense_TrackerV1.repository;

import com.finTech.Expense_TrackerV1.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
