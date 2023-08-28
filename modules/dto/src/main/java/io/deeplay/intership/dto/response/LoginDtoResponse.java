package io.deeplay.intership.dto.response;

public class LoginDtoResponse extends BaseDtoResponse {
    public final String status;
    public final String message;
    public final String token;


    public LoginDtoResponse(String status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }

    public LoginDtoResponse() {
        this(null, null, null);
    }
}
