package com.mygdx.game;


public class StoryScreen extends ScreenBeta {

    Scene scene;
    ActorBeta continueKey;

    public void initialize() {
        ActorBeta background = new ActorBeta(0, 0, mainStage);
        background.loadTexture("sprites/backgrounds/background0_59.png");
        background.setSize(WIDTH, HEIGHT);
        background.setOpacity(0);
    }

    @Override
    public void update(float dt) {

    }
}
