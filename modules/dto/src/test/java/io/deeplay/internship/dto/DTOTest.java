package io.deeplay.internship.dto;

import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.dto.request.RegistrationDTO;
import io.deeplay.intership.dto.response.RegistrationLogoutJoinDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DTOTest {
    @Test
    public void checkRegistrationRequest(){
        String login = "Nick";
        String password_hash = "3454465";
        RegistrationDTO registrationDTO = new RegistrationDTO(RequestType.REGISTRATION, login, password_hash);
        assertEquals(registrationDTO.login(), "Nick");
        assertEquals(registrationDTO.passwordHash(), "3454465");
    }

    @Test
    public void checkRegistrationResponse(){
        String message = "Registration was performed!";
        String status = "Success";
        RegistrationLogoutJoinDTO registrationLogoutJoinDTO = new RegistrationLogoutJoinDTO(message, status);
        assertEquals(registrationLogoutJoinDTO.message(), "Registration was performed!");
        assertEquals(registrationLogoutJoinDTO.status(), "Success");
    }
}
