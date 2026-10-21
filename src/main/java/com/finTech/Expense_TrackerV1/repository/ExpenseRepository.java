package com.finTech.Expense_TrackerV1.repository;

import com.finTech.Expense_TrackerV1.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("""
            SELECT e from Expense e
            JOIN FETCH e.category
            JOIN FETCH e.paymentMethod
            WHERE e.id = :expenseId AND e.user.id =:userId
            """
    )
    Optional<Expense> findByIdAndUser_IdWithDetails(@Param("expenseId") Long id,
                                                    @Param("userId") Long userId);

    Optional<Expense> findByIdAndUser_Id(Long id, Long userId);

    @Query("""
            SELECT e FROM Expense e
            JOIN FETCH e.category
            JOIN FETCH e.paymentMethod
            where e.user.id = :userId
            """)
    List<Expense> findByUser_IdWithDetails(@Param("userId") Long userId);
}