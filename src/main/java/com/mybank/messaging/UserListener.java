package com.mybank.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybank.messaging.dto.Payload;
import com.mybank.messaging.dto.UserDTO;
import com.mybank.service.UserService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
@EnableBinding(UserInput.class)
@Setter
@NoArgsConstructor
public class UserListener {
    @Autowired
    private UserService userService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public UserListener(UserService userService) {
        this.userService = userService;
    }

    @StreamListener(target = UserInput.INPUT)
    public void onTenantEvent(Message<Payload> message) {
        Payload<LinkedHashMap> payload = message.getPayload();
        UserDTO userDTO = objectMapper.convertValue(payload.getObjectToSend(), UserDTO.class);
        userService.insert(userDTO);
    }
}
