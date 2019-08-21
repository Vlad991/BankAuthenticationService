package com.mybank.messaging.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardDTO {
    @NotNull(message = "Number is required")
//    @CreditCardNumber(ignoreNonDigitCharacters = true)
    private String number;   // todo card number

    @NotNull(message = "Date is required")
    private CreditCardDate date;

    @NotNull(message = "Client is required")
    private UserDTO client;

    @NotNull(message = "Code is required")
    @Max(999)
    private int code;

    private int sum;

    @NotNull(message = "Status is required")
    private CreditCardStatus status;

    @NotNull(message = "Pin is required")
    private int pin;     // 0000 todo (validator)
}
