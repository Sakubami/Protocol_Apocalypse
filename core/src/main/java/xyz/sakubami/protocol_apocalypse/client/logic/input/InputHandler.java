package xyz.sakubami.protocol_apocalypse.client.logic.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import xyz.sakubami.protocol_apocalypse.ProtocolApocalypse;
import xyz.sakubami.protocol_apocalypse.client.Client;
import xyz.sakubami.protocol_apocalypse.client.logic.action.Action;
import xyz.sakubami.protocol_apocalypse.client.screens.GameScreen;
import xyz.sakubami.protocol_apocalypse.shared.Configuration;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.clienttoserver.C2SPlayerMovePacket;

public class InputHandler {
    private Client client;

    public void handle(Input input, Client client) {
        this.client = client;
        if (input.isKeyJustPressed(Input.Keys.W) || input.isKeyJustPressed(Input.Keys.A) || input.isKeyJustPressed(Input.Keys.S) || input.isKeyJustPressed(Input.Keys.D))
            move(input);


    }

    private void move(Input input) {
        client.getPrediction().sendMovement(input);
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
