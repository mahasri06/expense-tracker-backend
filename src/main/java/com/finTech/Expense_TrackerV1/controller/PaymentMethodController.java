package com.finTech.Expense_TrackerV1.controller;

import com.finTech.Expense_TrackerV1.dto.PaymentMethodDto.PaymentMethodRequest;
import com.finTech.Expense_TrackerV1.dto.PaymentMethodDto.PaymentMethodResponse;
import com.finTech.Expense_TrackerV1.entity.PaymentMethod;
import com.finTech.Expense_TrackerV1.exception.ApiResponse;
import com.finTech.Expense_TrackerV1.service.PaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllPaymentMethods(
            @RequestParam Long userId
    ) {
        List<PaymentMethod> paymentMethods =
                paymentMethodService.getAllPaymentMethod(userId);

        List<PaymentMethodResponse> responses = new ArrayList<>();

        for (PaymentMethod paymentMethod : paymentMethods) {
            PaymentMethodResponse response = new PaymentMethodResponse();
            response.setId(paymentMethod.getId());
            response.setName(paymentMethod.getName());
            response.setCreatedAt(paymentMethod.getCreatedAt());
            response.setUpdatedAt(paymentMethod.getUpdatedAt());

            responses.add(response);
        }

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Payment methods retrieved successfully",
                        null,
                        responses
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPaymentMethodById(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        PaymentMethod paymentMethod =
                paymentMethodService.getPaymentMethodById(id, userId);

        PaymentMethodResponse response = new PaymentMethodResponse();
        response.setId(paymentMethod.getId());
        response.setName(paymentMethod.getName());
        response.setCreatedAt(paymentMethod.getCreatedAt());
        response.setUpdatedAt(paymentMethod.getUpdatedAt());

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Payment method retrieved successfully",
                        null,
                        response
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createPaymentMethod(
            @Valid @RequestBody PaymentMethodRequest paymentMethodRequest,
            @RequestParam Long userId
    ) {
        PaymentMethod paymentMethod =
                paymentMethodService.createPaymentMethod(
                        paymentMethodRequest, userId
                );

        PaymentMethodResponse response = new PaymentMethodResponse();
        response.setId(paymentMethod.getId());
        response.setName(paymentMethod.getName());
        response.setCreatedAt(paymentMethod.getCreatedAt());
        response.setUpdatedAt(paymentMethod.getUpdatedAt());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse(
                                true,
                                "Payment method created successfully",
                                null,
                                response
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePaymentMethod(
            @Valid @RequestBody PaymentMethodRequest paymentMethodRequest,
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        PaymentMethod paymentMethod =
                paymentMethodService.updatePaymentMethod(
                        paymentMethodRequest, id, userId
                );

        PaymentMethodResponse response = new PaymentMethodResponse();
        response.setId(paymentMethod.getId());
        response.setName(paymentMethod.getName());
        response.setCreatedAt(paymentMethod.getCreatedAt());
        response.setUpdatedAt(paymentMethod.getUpdatedAt());

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Payment method updated successfully",
                        null,
                        response
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePaymentMethod(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        paymentMethodService.deletePaymentMethod(id, userId);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Payment method deleted successfully",
                        null,
                        null
                )
        );
    }
}
