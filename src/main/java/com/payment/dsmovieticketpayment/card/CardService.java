package com.payment.dsmovieticketpayment.card;

import com.payment.dsmovieticketpayment.card.payload.DeductAmountRequest;
import com.payment.dsmovieticketpayment.card.payload.ValidateCardRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public boolean validateCard(ValidateCardRequest validateCardRequest) {
        if (cardRepository.existsByCardTypeAndCardNumberAndCsv(validateCardRequest.getCardType(), validateCardRequest.getCardNumber(), validateCardRequest.getCsv()))
            return true;
        return false;
    }

    public boolean deductAmount(DeductAmountRequest deductAmountRequest) {
        CardEntity cardEntity = cardRepository.findByCardTypeAndCardNumberAndCsv(deductAmountRequest.getCardType(), deductAmountRequest.getCardNumber(), deductAmountRequest.getCsv())
                .orElse(null);

        if (cardEntity != null) {
            if (cardEntity.getAmount() >= deductAmountRequest.getAmount()) {
                cardEntity.setAmount(cardEntity.getAmount() - deductAmountRequest.getAmount());
                cardRepository.save(cardEntity);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}