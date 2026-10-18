package com.finTech.Expense_TrackerV1.exception;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException{

    private final ErrorCode errorCode;
    public DuplicateResourceException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode=errorCode;
    }
}
