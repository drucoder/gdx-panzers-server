package letscode.gdx.server.ws;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.socket.WebSocketSession;

public interface MessageListener {
    void handle(WebSocketSession session, JsonNode message);
}
