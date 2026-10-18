package com.finTech.Expense_TrackerV1.controller;

import com.finTech.Expense_TrackerV1.dto.CategoryDto.CategoryRequest;
import com.finTech.Expense_TrackerV1.dto.CategoryDto.CategoryResponse;
import com.finTech.Expense_TrackerV1.entity.Category;
import com.finTech.Expense_TrackerV1.exception.ApiResponse;
import com.finTech.Expense_TrackerV1.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories(
            @RequestParam Long userId
    ){
        List<Category> categories =categoryService.getAllCategory(userId);

        List<CategoryResponse> responses = new ArrayList<>();

        for(Category category : categories){
            CategoryResponse categoryResponse = new CategoryResponse();

            categoryResponse.setId(category.getId());
            categoryResponse.setName(category.getName());
            categoryResponse.setCreatedAt(category.getCreatedAt());
            categoryResponse.setUpdatedAt(category.getUpdatedAt());

            responses.add(categoryResponse);
        }
        return ResponseEntity.ok(
                new ApiResponse(
                        true, "Categories retrieved successfully", null, responses
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(
            @PathVariable Long id,
            @RequestParam Long userId
    ){
        Category category = categoryService.getCategoryById(id, userId);

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setCreatedAt(category.getCreatedAt());
        categoryResponse.setUpdatedAt(category.getUpdatedAt());

        return ResponseEntity.ok(
                new ApiResponse(
                        true, "Category retrieved successfully", null, categoryResponse
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(
            @Valid @RequestBody CategoryRequest categoryRequest,
            @RequestParam Long userId
    ){
        Category category = categoryService.createCategory(categoryRequest, userId);

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setCreatedAt(category.getCreatedAt());
        categoryResponse.setUpdatedAt(category.getUpdatedAt());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse(true, "Category created successfully", null, categoryResponse)
                );
    }
}
