package com.payment.dsmovieticketpayment.card.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCardRequest {

    private String cardType;
    private String cardNumber;
    private String csv;

}