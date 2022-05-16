package com.payment.dsmovieticketpayment.card.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductAmountRequest {

    private String cardType;
    private String cardNumber;
    private String csv;
    private String cardHolder;
    private float amount;

}