package io.deeplay.intership.dto.response;

public class FailureDtoResponse extends BaseDtoResponse {
    public final String status;
    public final String message;

    public FailureDtoResponse(ResponseType responseType, String status, String message) {
        super(responseType);
        this.status = status;
        this.message = message;
    }

    public FailureDtoResponse(String status, String message) {
        this(ResponseType.FAILURE, status, message);
    }
}
