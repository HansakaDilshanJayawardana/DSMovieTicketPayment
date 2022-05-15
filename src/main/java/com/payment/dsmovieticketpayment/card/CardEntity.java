package com.payment.dsmovieticketpayment.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_card")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "card_type" , nullable = false)
    private String cardType;

    @Column(name = "card_holder" ,  nullable = false)
    private String cardHolder;

    @Column(name = "holder_phone" , nullable = false)
    private String cardHolderPhone;

    @Column(name = "card_number" , nullable = false)
    @Size(min = 16 , max = 16)
    private String cardNumber;

    @Column(name = "card_csv" , nullable = false)
    @Size(min = 3 , max = 3)
    private String csv;

    @Column(name = "amount" , nullable = false)
    private Float amount = 0f;

}