package com.mybank.messaging;

import com.mybank.messaging.dto.CreditCardDTO;
import com.mybank.messaging.dto.Payload;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(CreditCardInput.class)
@Setter
@NoArgsConstructor
public class CreditCardListener {
    CreditCardService creditCardService;

    public CreditCardListener(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @StreamListener(target = CreditCardInput.INPUT)
    public void onTenantEvent(Message<Payload> message) {
        Payload<CreditCardDTO> payload = message.getPayload();
        CreditCardDTO creditCardDTO = payload.getObjectToSend();
        creditCardService.insert(creditCardDTO);
    }
}
