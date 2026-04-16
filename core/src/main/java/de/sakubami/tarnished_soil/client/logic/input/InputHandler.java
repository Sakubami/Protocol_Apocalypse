package de.sakubami.tarnished_soil.client.logic.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import de.sakubami.tarnished_soil.client.logic.Prediction;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.Direction;
import de.sakubami.tarnished_soil.shared.utils.Vector2f;

public class InputHandler extends InputAdapter {

    public void handle(Input input, Prediction prediction, float deltaTime, Camera camera) {
        if (input.isKeyPressed(Input.Keys.W) ||
            input.isKeyPressed(Input.Keys.A) ||
            input.isKeyPressed(Input.Keys.S) ||
            input.isKeyPressed(Input.Keys.D))
            move(input, prediction, deltaTime);
        if (input.isButtonJustPressed(Input.Buttons.RIGHT) || input.isButtonPressed(Input.Buttons.LEFT))
            singleClick(input, prediction, camera);
    }

    private void move(Input input, Prediction prediction, float deltaTime) {
        float speed = 110f;
        float x = 0, y = 0;
        Direction direction = Direction.EAST;

        if (input.isKeyPressed(Input.Keys.W)) { y +=1; direction = Direction.NORTH; }
        if (input.isKeyPressed(Input.Keys.S)) { y -=1; direction = Direction.SOUTH; }
        if (input.isKeyPressed(Input.Keys.D)) x +=1;
        if (input.isKeyPressed(Input.Keys.A)) { x -=1; direction = Direction.WEST; }

        Vector2f movement = new Vector2f(x, y);
        if (movement.length() > 0) movement = movement.normalized();
        Vector2f velocity = movement.mul(speed);
        prediction.sendMovement(velocity, deltaTime, direction);
    }

    private void singleClick(Input input, Prediction prediction, Camera camera) {
        Vector3 worldPos = camera.unproject(new Vector3(input.getX(), input.getY(), 0));
        Vector2f pos = new Vector2f(worldPos.x, worldPos.y);
        if (input.isButtonPressed(Input.Buttons.RIGHT))
            prediction.sendInteract(pos);
        else
            prediction.sendAttack(pos);
    }
}
