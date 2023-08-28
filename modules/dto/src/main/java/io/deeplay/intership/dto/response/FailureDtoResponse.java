package io.deeplay.intership.dto.response;

public class FailureDtoResponse extends BaseDtoResponse {
    public final String status;
    public final String message;


    public FailureDtoResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public FailureDtoResponse() {
        this(null, null);
    }
}
