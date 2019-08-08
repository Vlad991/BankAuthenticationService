package com.mybank.messaging.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {
    @NotNull(message = "Country is required")
    private String country;

    @NotNull(message = "City is required")
    private String city;

    @NotNull(message = "Street is required")
    private String street;

    @NotNull(message = "Postcode is required")
    private int postcode;

//    private List<UserDTO> userList; //list of users, who live at this address
}
