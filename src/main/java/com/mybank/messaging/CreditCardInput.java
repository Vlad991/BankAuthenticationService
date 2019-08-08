package com.mybank.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CreditCardInput {
    String INPUT = "card-event-input";  //chanel name

    @Input(INPUT)
    SubscribableChannel input();        //implementation is autowired
}
