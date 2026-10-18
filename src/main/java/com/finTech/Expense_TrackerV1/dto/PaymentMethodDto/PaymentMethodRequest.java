package com.finTech.Expense_TrackerV1.dto.PaymentMethodDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodRequest {

    @NotBlank(message = "Payment method name must not be blank")
    @Size(max = 100, message = "Payment method name must not exceed 100 characters")
    private String name;
}