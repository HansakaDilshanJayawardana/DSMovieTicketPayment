package com.payment.dsmovieticketpayment.auth.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequestDto {

    private String userName;
    private String password;

}