package letscode.gdx.server;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import letscode.gdx.server.actors.Panzer;
import letscode.gdx.server.ws.WebSocketHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

@Component
public class GameLoop extends ApplicationAdapter {
    private static final float frameRate = 1 / 2f;
    private final WebSocketHandler socketHandler;
    private final Json json;
    private float lastRender = 0;
    private final ObjectMap<String, Panzer> panzers = new ObjectMap<>();
    private final ForkJoinPool pool = ForkJoinPool.commonPool();

    public GameLoop(WebSocketHandler socketHandler, Json json) {
        this.socketHandler = socketHandler;
        this.json = json;
    }

    @Override
    public void create() {
        socketHandler.setConnectListener(session -> {
            Panzer panzer = new Panzer();
            panzer.setId(session.getId());
            panzers.put(session.getId(), panzer);
            try {
                session.getNativeSession().getBasicRemote().sendText(session.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        socketHandler.setDisconnectListener(session -> {
            panzers.remove(session.getId());
        });
        socketHandler.setMessageListener(((session, message) -> {
            pool.execute(() -> {
                String type = message.getString("type");
                switch (type) {
                    case "state":
                        Panzer panzer = panzers.get(session.getId());
                        panzer.setLeftPressed(message.getBoolean("leftPressed"));
                        panzer.setRightPressed(message.getBoolean("rightPressed"));
                        panzer.setUpPressed(message.getBoolean("upPressed"));
                        panzer.setDownPressed(message.getBoolean("downPressed"));
                        panzer.setAngle(message.getFloat("angle"));
                        break;
                    default:
                        throw new RuntimeException("Unknown WS object type: " + type);
                }
            });
        }));
    }

    @Override
    public void render() {
        lastRender += Gdx.graphics.getDeltaTime();
        if (lastRender >= frameRate) {
            for (ObjectMap.Entry<String, Panzer> panzerEntry : panzers) {
                Panzer panzer = panzerEntry.value;
                panzer.act(lastRender);
            }

            lastRender = 0;

            pool.execute(() -> {
                String stateJson = json.toJson(panzers);
                for (StandardWebSocketSession session : socketHandler.getSessions()) {
                    try {
                        session.getNativeSession().getBasicRemote().sendText(stateJson);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
