package letscode.gdx.server.config;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import letscode.gdx.server.GameLoop;
import letscode.gdx.server.actors.Panzer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public HeadlessApplication getApplication(GameLoop gameLoop) {
        return new HeadlessApplication(gameLoop);
    }

    @Bean
    public Json getJson() {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        json.addClassTag("panzer", Panzer.class);
        return json;
    }
}
