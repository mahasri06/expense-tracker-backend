package com.finTech.Expense_TrackerV1.repository;

import com.finTech.Expense_TrackerV1.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Optional<Expense> findByIdAndUser_Id(Long id, Long userId);
    List<Expense> findByUser_Id(Long userId);
}