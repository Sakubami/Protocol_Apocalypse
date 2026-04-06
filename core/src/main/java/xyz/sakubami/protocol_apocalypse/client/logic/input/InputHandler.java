package xyz.sakubami.protocol_apocalypse.client.logic.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import xyz.sakubami.protocol_apocalypse.ProtocolApocalypse;
import xyz.sakubami.protocol_apocalypse.client.Client;
import xyz.sakubami.protocol_apocalypse.client.logic.Prediction;
import xyz.sakubami.protocol_apocalypse.client.logic.action.Action;
import xyz.sakubami.protocol_apocalypse.client.screens.GameScreen;
import xyz.sakubami.protocol_apocalypse.shared.Configuration;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.clienttoserver.C2SPlayerMovePacket;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.util.Vector;

public class InputHandler {
    public void handle(Input input, Prediction prediction, float deltaTime) {
        if (input.isKeyPressed(Input.Keys.W) ||
            input.isKeyPressed(Input.Keys.A) ||
            input.isKeyPressed(Input.Keys.S) ||
            input.isKeyPressed(Input.Keys.D))
            move(input, prediction, deltaTime);
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

    private void interact() {
        // local prediction
        // right click
        // send player input packet to server
    }

    private void attack() {
        // left click
    }
}
