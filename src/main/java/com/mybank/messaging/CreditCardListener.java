package com.mybank.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybank.messaging.dto.CreditCardDTO;
import com.mybank.messaging.dto.Payload;
import com.mybank.service.CreditCardService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
@EnableBinding(CreditCardInput.class)
@Setter
@NoArgsConstructor
public class CreditCardListener {
    @Autowired
    private CreditCardService creditCardService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public CreditCardListener(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @StreamListener(target = CreditCardInput.INPUT)
    public void onTenantEvent(Message<Payload> message) {
        Payload<LinkedHashMap> payload = message.getPayload();
        CreditCardDTO creditCardDTO = objectMapper.convertValue(payload.getObjectToSend(), CreditCardDTO.class);
        creditCardService.insert(creditCardDTO);
    }
}
