package com.payment.dsmovieticketpayment.auth.exceptions;

public class FieldValidationFailed extends RuntimeException{

    public FieldValidationFailed(String message) {
        super(message);
    }

}