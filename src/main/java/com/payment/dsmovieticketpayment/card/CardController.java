package com.payment.dsmovieticketpayment.card;

import com.payment.dsmovieticketpayment.card.payload.DeductAmountRequest;
import com.payment.dsmovieticketpayment.card.payload.ValidateCardRequest;
import com.payment.dsmovieticketpayment.common.FieldValidationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/card")
@AllArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/validate-card")
    public ResponseEntity<Boolean> validateCard(@RequestBody ValidateCardRequest validateCardRequest) {
        if (validateCardRequest.getCardType().isEmpty()) {
            throw new FieldValidationException("Card Type is Required");
        }
        if (!(validateCardRequest.getCardType().equals(CardType.VISA) || validateCardRequest.getCardType().equals(CardType.MASTER) || validateCardRequest.getCardType().equals(CardType.AMEX))) {
            throw new FieldValidationException("Card Type Miss Match");
        }
        if (validateCardRequest.getCardNumber().isEmpty()) {
            throw new FieldValidationException("Card Number is Required");
        }
        if (validateCardRequest.getCardNumber().length() != 16) {
            throw new FieldValidationException("Card Number is not Valid");
        }

        return new ResponseEntity<>(cardService.validateCard(validateCardRequest), HttpStatus.OK);
    }

    @PostMapping("/deduct-amount")
    public ResponseEntity<Boolean> deductAmount(@RequestBody DeductAmountRequest deductAmountRequest) {
        if (deductAmountRequest.getCardType().isEmpty()) {
            throw new FieldValidationException("Card Type is Required");
        }
        if (!(deductAmountRequest.getCardType().equals(CardType.VISA) || deductAmountRequest.getCardType().equals(CardType.MASTER) || deductAmountRequest.getCardType().equals(CardType.AMEX))) {
            throw new FieldValidationException("Card Type Miss Match");
        }
        if (deductAmountRequest.getCardNumber().isEmpty()) {
            throw new FieldValidationException("Card Number is Required");
        }
        if (deductAmountRequest.getCardNumber().length() != 16) {
            throw new FieldValidationException("Card Number is not Valid");
        }
        if (deductAmountRequest.getAmount() <= 0) {
            throw new FieldValidationException("Give Proper Amount");
        }

        boolean deductResult = cardService.deductAmount(deductAmountRequest);

        if (deductResult) {
            return new ResponseEntity<>(deductResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(deductResult, HttpStatus.BAD_GATEWAY);
        }
    }

}