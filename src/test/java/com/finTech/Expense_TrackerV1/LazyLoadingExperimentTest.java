
package com.finTech.Expense_TrackerV1;

import com.finTech.Expense_TrackerV1.entity.Expense;
import com.finTech.Expense_TrackerV1.repository.ExpenseRepository;
import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class LazyLoadingExperimentTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    void demonstrateLazyLoadingException() {

        Expense expense = expenseRepository
                .findByIdAndUser_Id(1L, 1L)
                .orElseThrow();

        System.out.println(
                "Is category loaded? "
                        + Hibernate.isInitialized(expense.getCategory())
        );

        assertFalse(
                Hibernate.isInitialized(expense.getCategory())
        );

        assertThrows(
                LazyInitializationException.class,
                () -> expense.getCategory().getName()
        );
    }
}
