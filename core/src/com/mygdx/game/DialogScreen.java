package com.mygdx.game;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class DialogScreen extends ScreenBeta {

    Music dialogueMusic;

    Sound vegeta1d;
    Sound vegeta2d;
    Sound goku1d;

    Touchpad touchpad;

    Skin skin;
    Skin uiSkin;

    private float timeCountTo;

    ActorBeta foreground;
    ActorBeta foreground1;
    ActorBeta foreground2;
    int dialogCounter;

    @Override
    public void initialize() {

        dialogueMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/dialogue.mp3"));
        dialogueMusic.setLooping(true);

        dialogueMusic.setVolume(0.5f);
        dialogueMusic.play();

        timeCountTo = 0;

        //MyGame.menuScreen.selectionSfx.stop();


        vegeta1d = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/vegetaDialog.mp3"));
        vegeta2d = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/vegetaDialog2.mp3"));

        goku1d = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/gokuDialog.mp3"));

        dialogCounter = 0;

        ActorBeta.setWorldBounds(WIDTH, HEIGHT);

        foreground2 = new ActorBeta(0, 0, mainStage);
        foreground2.loadTexture("sprites/backgrounds/vegeta2.png");
        foreground2.setSize(WIDTH, HEIGHT);

        foreground1 = new ActorBeta(0, 0, mainStage);
        foreground1.loadTexture("sprites/backgrounds/goku1.png");
        foreground1.setSize(WIDTH, HEIGHT);

        foreground = new ActorBeta(0, 0, mainStage);
        foreground.loadTexture("sprites/backgrounds/vegeta1.png");
        foreground.setSize(WIDTH, HEIGHT);

        /*background = new ActorBeta(900, 300, mainStage);
        background.loadTexture("sprites/backgrounds/background0_20.png");
        background.setScale(2.0f);*/

        skin = new Skin(Gdx.files.internal("skins/pixthulhu/skin/pixthulhu-ui.json"));
        uiSkin = new Skin(Gdx.files.internal("skins/arcade/skin/arcade-ui.json"));

        uiStage.addActor(tableContainer);
        //Touchpad
        touchpad = new Touchpad(40.0f, skin, "default");
        touchpad.setPosition(WIDTH / 5, HEIGHT / 3);
        touchpad.setResetOnTouchUp(true);
        touchpad.getColor().a = 0.0f;

        uiTable.add(touchpad).width(touchpad.getWidth() * 1.5f).height(touchpad.getHeight() * 1.5f).padRight(800).padTop(600);

        Button aButton = new Button(uiSkin, "red");
        Button bButton = new Button(uiSkin, "blue");
        aButton.getColor().a = 0.0f;
        bButton.getColor().a = 1.0f;

        uiTable.padRight(50).add(aButton).width(aButton.getWidth() * 2.0f).height(aButton.getHeight() * 2.0f).bottom().padRight(120);
        uiTable.add(bButton).width(bButton.getWidth() * 2.0f).height(bButton.getHeight() * 2.0f).bottom().padBottom(120);





        touchpad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float deltaX = ((Touchpad) actor).getKnobPercentX();
                float deltaY = ((Touchpad) actor).getKnobPercentY();

                Gdx.app.log("Delta X", "" + deltaX);
                Gdx.app.log("Delta Y", "" + deltaY);
            }
        });

        bButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
              dialogCounter = dialogCounter + 1;

                if (dialogCounter == 1){
                    vegeta1d.stop();
                    goku1d.play();
                    foreground.remove();
                }

                if(dialogCounter == 2){
                    goku1d.stop();
                    vegeta2d.play();
                    foreground1.remove();
                }

                if(dialogCounter == 3) {
                    vegeta2d.stop();
                    dialogueMusic.stop();
                    MyGame.gameScreen = new GameScreen();
                    MyGame.setActiveScreen(MyGame.gameScreen);
                }
            }
        });

    }

    @Override
    public void update(float dt) {
        //blueRanger.act(dt);
        touchpad.act(dt);

        timeCountTo += dt;

        if(timeCountTo >1) {
            vegeta1d.play();
            timeCountTo = -1000;
        }


        if(touchpad.getKnobPercentX() > 0.5 && touchpad.getKnobPercentX() < 0.9) {
            Gdx.app.log("Delta X", "Knob X is " + touchpad.getKnobPercentX());
        }

        if(touchpad.getKnobPercentY() > 0.5 && touchpad.getKnobPercentY() < 0.9) {
            Gdx.app.log("Delta Y", "Knob Y is " + touchpad.getKnobPercentX());
        }

    }
}
