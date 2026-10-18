package com.finTech.Expense_TrackerV1.service;

import com.finTech.Expense_TrackerV1.dto.CategoryDto.CategoryRequest;
import com.finTech.Expense_TrackerV1.entity.Category;
import com.finTech.Expense_TrackerV1.entity.User;
import com.finTech.Expense_TrackerV1.exception.DuplicateResourceException;
import com.finTech.Expense_TrackerV1.exception.ErrorCode;
import com.finTech.Expense_TrackerV1.exception.ResourceNotFoundException;
import com.finTech.Expense_TrackerV1.repository.CategoryRepository;
import com.finTech.Expense_TrackerV1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository
                           ,UserRepository userRepository){
        this.categoryRepository = categoryRepository;
        this.userRepository =userRepository;
    }

    public List<Category> getAllCategory(Long userId){
        userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found",
                        ErrorCode.USER_NOT_FOUND)
        );

        return categoryRepository.findByUser_Id(userId);
    }

    public Category getCategoryById(Long id, Long userId){
        Category category = categoryRepository.findByIdAndUser_Id(id, userId).orElseThrow(
                () -> new ResourceNotFoundException("Category id: " + id + " not found for the user : " + userId, ErrorCode.CATEGORY_NOT_FOUND)
        );
        return category;
    }

    public Category createCategory(CategoryRequest categoryRequest, Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found",
                        ErrorCode.USER_NOT_FOUND)
        );

        boolean existingCheck = categoryRepository.existsByUser_IdAndName(userId, categoryRequest.getName());

        if(existingCheck){
            throw new DuplicateResourceException(
                    "Category already exists",ErrorCode.CATEGORY_ALREADY_IN_USE
            );
        }

        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setUser(user);

        return categoryRepository.save(category);
    }

    public Category updateCategory(
            CategoryRequest request,
            Long id,
            Long userId
    ){
        Category category = categoryRepository.findByIdAndUser_Id(
                id, userId
        ).orElseThrow(
                () -> new ResourceNotFoundException("Category id: " + id + " not found",
                        ErrorCode.CATEGORY_NOT_FOUND)
        );
        boolean existingCheck = categoryRepository.existsByUser_IdAndName(userId, request.getName());

        if(existingCheck){
            throw new DuplicateResourceException(
                    "Category already exists",ErrorCode.CATEGORY_ALREADY_IN_USE
            );
        }

        category.setName(request.getName());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id, Long userId){
        Category category = categoryRepository.findByIdAndUser_Id(id, userId).orElseThrow(
                () -> new ResourceNotFoundException("Category id: "+ id + " not found", ErrorCode.CATEGORY_NOT_FOUND)
        );

        categoryRepository.delete(category);
    }
}
