package io.deeplay.intership.dto.response;

public class InfoDtoResponse extends BaseDtoResponse {
    public final String status;
    public final String message;


    public InfoDtoResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public InfoDtoResponse() {
        this(null, null);
    }
}
