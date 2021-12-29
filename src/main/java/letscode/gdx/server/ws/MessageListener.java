package letscode.gdx.server.ws;

import org.springframework.web.socket.WebSocketSession;

public interface MessageListener {
    void handle(WebSocketSession session, String message);
}
