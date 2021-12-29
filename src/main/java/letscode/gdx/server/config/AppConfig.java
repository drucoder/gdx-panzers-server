package letscode.gdx.server.config;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import letscode.gdx.server.GameLoop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public HeadlessApplication getApplication(GameLoop gameLoop) {
        return new HeadlessApplication(gameLoop);
    }
}
