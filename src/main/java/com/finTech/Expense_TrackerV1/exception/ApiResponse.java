package com.finTech.Expense_TrackerV1.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private boolean success;

    private String message;

    private ErrorCode errorCode;

    private Object data;
}
