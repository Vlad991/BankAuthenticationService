package com.mybank.messaging;

import com.mybank.messaging.dto.Payload;
import com.mybank.messaging.dto.UserDTO;
import com.mybank.service.UserService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(UserInput.class)
@Setter
@NoArgsConstructor
public class UserListener {
    UserService userService;

    public UserListener(UserService userService) {
        this.userService = userService;
    }

    @StreamListener(target = UserInput.INPUT)
    public void onTenantEvent(Message<Payload> message) {
        Payload<UserDTO> payload = message.getPayload();
        UserDTO userDTO = payload.getObjectToSend();
        userService.insert(userDTO);
    }
}
