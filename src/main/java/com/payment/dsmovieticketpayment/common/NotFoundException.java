package com.payment.dsmovieticketpayment.common;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }

}