package com.finTech.Expense_TrackerV1.repository;

import com.finTech.Expense_TrackerV1.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByIdAndUser_Id(Long id, Long userId);
    List<Category> findByUser_Id(Long userId);
}
