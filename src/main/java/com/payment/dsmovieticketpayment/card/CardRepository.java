package com.payment.dsmovieticketpayment.card;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity , Long> {

    boolean existsByCardTypeAndCardNumberAndCsv(String cardType, String cardNumber, String csv);
    Optional<CardEntity> findByCardTypeAndCardNumberAndCsv(String cardType, String cardNumber, String csv);

}