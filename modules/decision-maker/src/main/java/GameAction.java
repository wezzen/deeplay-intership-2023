import io.deeplay.intership.dto.RequestType;

public record GameAction(RequestType type, int col, int row) {
}
