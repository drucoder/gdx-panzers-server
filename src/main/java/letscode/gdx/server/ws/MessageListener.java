package letscode.gdx.server.ws;

import com.badlogic.gdx.utils.JsonValue;
import org.springframework.web.socket.WebSocketSession;

public interface MessageListener {
    void handle(WebSocketSession session, JsonValue message);
}
