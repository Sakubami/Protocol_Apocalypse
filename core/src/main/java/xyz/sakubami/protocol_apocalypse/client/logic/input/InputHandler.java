package xyz.sakubami.protocol_apocalypse.client.logic.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import xyz.sakubami.protocol_apocalypse.client.logic.Prediction;
import xyz.sakubami.protocol_apocalypse.shared.utils.Coordinates;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

public class InputHandler {
    public void handle(Input input, Prediction prediction, float deltaTime, Camera camera) {
        if (input.isKeyPressed(Input.Keys.W) ||
            input.isKeyPressed(Input.Keys.A) ||
            input.isKeyPressed(Input.Keys.S) ||
            input.isKeyPressed(Input.Keys.D))
            move(input, prediction, deltaTime);
        if (input.isButtonJustPressed(Input.Buttons.RIGHT))
            singleInteract(input, prediction, camera);
    }

    private void move(Input input, Prediction prediction, float deltaTime) {
        float speed = 150f;
        float x = 0, y = 0;

        if (input.isKeyPressed(Input.Keys.W)) y +=1;
        if (input.isKeyPressed(Input.Keys.S)) y -=1;
        if (input.isKeyPressed(Input.Keys.D)) x +=1;
        if (input.isKeyPressed(Input.Keys.A)) x -=1;

        Vector2f direction = new Vector2f(x, y);
        if (direction.length() > 0) direction = direction.normalized();

        Vector2f velocity = direction.mul(speed);
        prediction.sendMovement(velocity, deltaTime);
    }

    private void singleInteract(Input input, Prediction prediction, Camera camera) {
        Vector3 worldPos = camera.unproject(new Vector3(input.getX(), input.getY(), 0));
        Vector2f pos = new Vector2f(worldPos.x, worldPos.y);
        System.out.println("CLICKED ABSOLUTE POSITION: " + pos);
        prediction.sendInteract(pos);
        // local prediction
        // right click
        // send player input packet to server
    }

    private void attack() {
        // left click
    }
}
