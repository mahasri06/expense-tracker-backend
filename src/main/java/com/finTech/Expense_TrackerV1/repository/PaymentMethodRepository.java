package com.finTech.Expense_TrackerV1.repository;

import com.finTech.Expense_TrackerV1.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    Optional<PaymentMethod> findByIdAndUser_Id(Long id, Long userId);
}
