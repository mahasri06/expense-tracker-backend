package com.finTech.Expense_TrackerV1.service;

import com.finTech.Expense_TrackerV1.dto.PaymentMethodDto.PaymentMethodRequest;
import com.finTech.Expense_TrackerV1.entity.PaymentMethod;
import com.finTech.Expense_TrackerV1.entity.User;
import com.finTech.Expense_TrackerV1.exception.DuplicateResourceException;
import com.finTech.Expense_TrackerV1.exception.ErrorCode;
import com.finTech.Expense_TrackerV1.exception.ResourceNotFoundException;
import com.finTech.Expense_TrackerV1.repository.PaymentMethodRepository;
import com.finTech.Expense_TrackerV1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final UserRepository userRepository;

    public PaymentMethodService(
            PaymentMethodRepository paymentMethodRepository,
            UserRepository userRepository
    ) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.userRepository = userRepository;
    }

    public List<PaymentMethod> getAllPaymentMethod(Long userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "User not found",
                        ErrorCode.USER_NOT_FOUND
                )
        );

        return paymentMethodRepository.findByUser_Id(userId);
    }

    public PaymentMethod getPaymentMethodById(Long id, Long userId) {
        return paymentMethodRepository.findByIdAndUser_Id(id, userId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Payment method id: " + id + " not found",
                                ErrorCode.PAYMENT_METHOD_NOT_FOUND
                        )
                );
    }

    public PaymentMethod createPaymentMethod(
            PaymentMethodRequest paymentMethodRequest,
            Long userId
    ) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "User not found",
                        ErrorCode.USER_NOT_FOUND
                )
        );

        boolean existingCheck =
                paymentMethodRepository.existsByUser_IdAndName(
                        userId, paymentMethodRequest.getName()
                );

        if (existingCheck) {
            throw new DuplicateResourceException(
                    "Payment method already exists",
                    ErrorCode.PAYMENT_METHOD_ALREADY_IN_USE
            );
        }

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setName(paymentMethodRequest.getName());
        paymentMethod.setUser(user);

        return paymentMethodRepository.save(paymentMethod);
    }

    public PaymentMethod updatePaymentMethod(
            PaymentMethodRequest request,
            Long id,
            Long userId
    ) {
        PaymentMethod paymentMethod =
                paymentMethodRepository.findByIdAndUser_Id(id, userId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Payment method id: " + id + " not found",
                                        ErrorCode.PAYMENT_METHOD_NOT_FOUND
                                )
                        );

        boolean existingCheck =
                paymentMethodRepository.existsByUser_IdAndName(
                        userId, request.getName()
                );

        if (existingCheck) {
            throw new DuplicateResourceException(
                    "Payment method already exists",
                    ErrorCode.PAYMENT_METHOD_ALREADY_IN_USE
            );
        }

        paymentMethod.setName(request.getName());

        return paymentMethodRepository.save(paymentMethod);
    }

    public void deletePaymentMethod(Long id, Long userId) {
        PaymentMethod paymentMethod =
                paymentMethodRepository.findByIdAndUser_Id(id, userId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Payment method id: " + id + " not found",
                                        ErrorCode.PAYMENT_METHOD_NOT_FOUND
                                )
                        );

        paymentMethodRepository.delete(paymentMethod);
    }
}