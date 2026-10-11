package com.finTech.Expense_TrackerV1.repository;

import com.finTech.Expense_TrackerV1.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
