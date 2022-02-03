package letscode.gdx.server.actors;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Panzer implements Json.Serializable {
    private String id;

    private float angle;
    private float x;
    private float y;
    private int speed = 300;

    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;

    public void act(float delta) {
        float stepLength = speed * delta;
        if (isLeftPressed()) x -= stepLength;
        if (isRightPressed()) x += stepLength;
        if (isUpPressed()) y += stepLength;
        if (isDownPressed()) y -= stepLength;
    }

    @Override
    public void write(Json json) {
        json.writeValue("x", x);
        json.writeValue("y", y);
        json.writeValue("angle", angle);
        json.writeValue("id", id);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }
}
