package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import java.awt.Menu;
import java.util.ArrayList;

/**
 * Created by markapptist on 2018-11-12.
 */

public class GameScreen extends ScreenBeta {

    Music fightMusic;
    Vegeta vegeta;
    Goku goku;

    Sound attack1;
    Sound vegetaLost;
    Sound gokuLost;
    Sound vegetaDmg;
    Sound gokuDmg;

    Touchpad touchpad;

    Skin skin;
    Skin uiSkin;

    ActorBeta background;
    ActorBeta gokuPort;
    ActorBeta vegetaPort;
    int SPEED;

    ActorBeta gokuHealthBar;
    ActorBeta vegetaHealthBar;
    ActorBeta redBarGoku;
    ActorBeta redBarVegeta;

    private Integer worldTimer;
    private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;

    private Label countdownLabel;
    private Label winnerLabel;


    boolean firstBool;

    float vegetaHealth;//0 = dead, 1 = full health
    float gokuHealth;//0 = dead, 1 = full health

    TextButton againButton;

    boolean playOnce;

    ArrayList<gokuAttack> gokuAttacks;
    ArrayList<vegetaAttack> vegetaAttacks;
    int iArray;
    int vArray;

    @Override
    public void initialize() {
        iArray = 0;
        vArray = 0;
        gokuAttacks = new ArrayList<gokuAttack>();
        vegetaAttacks = new ArrayList<vegetaAttack>();
        vegetaHealth = 1.0f;
        gokuHealth = 1.0f;
        SPEED = 10;
        worldTimer = 180;
        timeCount = 0;
        timeUp = false;


        playOnce= true;



        countdownLabel = new Label("LABEL", labelStyle);
        countdownLabel.setPosition(WIDTH/2-50,HEIGHT-120);
        countdownLabel.setScale(1);
        countdownLabel.setFontScale(2);
        countdownLabel.setText("180");

        gokuHealthBar = new ActorBeta();
        gokuHealthBar.loadTexture("sprites/others/blank.png");
        gokuHealthBar.setOrigin(gokuHealthBar.getX()/2,gokuHealthBar.getY()/2);
        gokuHealthBar.setPosition(120,HEIGHT-120);
        gokuHealthBar.setScale(50*gokuHealth,4f);

        redBarGoku = new ActorBeta();
        redBarGoku.loadTexture("sprites/others/red.png");
        redBarGoku.setOrigin(redBarGoku.getX()/2,redBarGoku.getY()/2);
        redBarGoku.setPosition(120,HEIGHT-120);
        redBarGoku.setScale(50.2f,4.2f);

        vegetaHealthBar = new ActorBeta();
        vegetaHealthBar.loadTexture("sprites/others/blank.png");
        vegetaHealthBar.setOrigin(vegetaHealthBar.getX()/2,vegetaHealthBar.getY()/2);
        vegetaHealthBar.setPosition(WIDTH-110,HEIGHT-80);
        vegetaHealthBar.rotateBy(180);
        vegetaHealthBar.setScale(50*vegetaHealth,4f);

        redBarVegeta = new ActorBeta();
        redBarVegeta.loadTexture("sprites/others/red.png");
        redBarVegeta.setOrigin(redBarVegeta.getX()/2,redBarVegeta.getY()/2);
        redBarVegeta.setPosition(WIDTH-610,HEIGHT-120);
        redBarVegeta.setScale(50.2f,4.2f);

        ///SOUNDS

        fightMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/fight.mp3"));
        fightMusic.setLooping(true);
        fightMusic.setVolume(0.0f);
        fightMusic.play();

        attack1 = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/attack.wav"));
        vegetaLost = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/vegetaDies.mp3"));
        gokuLost = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/gokuDies.mp3"));
        vegetaDmg = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/vegetaAh.mp3"));
        gokuDmg = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/gokuAh.mp3"));

        ///

        ActorBeta.setWorldBounds(WIDTH, HEIGHT);

        background = new ActorBeta(0, 0, mainStage);
        background.loadTexture("sprites/backgrounds/background.png");
        background.setSize(WIDTH, HEIGHT);

        gokuPort = new ActorBeta(-60,HEIGHT-200, mainStage);
        gokuPort.loadTexture("sprites/goku/gokuPortrait.png");
        gokuPort.scaleBy(-0.5f);

        vegetaPort = new ActorBeta(WIDTH-180,HEIGHT-200, mainStage);
        vegetaPort.loadTexture("sprites/vegeta/vegetaPortrait.png");
        vegetaPort.scaleBy(-0.5f);

        skin = new Skin(Gdx.files.internal("skins/pixthulhu/skin/pixthulhu-ui.json"));
        uiSkin = new Skin(Gdx.files.internal("skins/arcade/skin/arcade-ui.json"));

        uiStage.addActor(tableContainer);
        //Touchpad
        touchpad = new Touchpad(40.0f, skin, "default");
        touchpad.setPosition(WIDTH / 5, HEIGHT / 3);
        touchpad.setResetOnTouchUp(true);
        touchpad.getColor().a = 1.0f;

        if(!firstBool) {
            uiTable.add(touchpad).width(touchpad.getWidth() * 1.5f).height(touchpad.getHeight() * 1.5f).padRight(800).padTop(600);
        }

        Button aButton = new Button(uiSkin, "red");
        Button bButton = new Button(uiSkin, "blue");
        aButton.getColor().a = 1.0f;
        bButton.getColor().a = 1.0f;


            uiTable.padRight(50).add(aButton).width(aButton.getWidth() * 2.0f).height(aButton.getHeight() * 2.0f).bottom().padRight(120);
            uiTable.add(bButton).width(bButton.getWidth() * 2.0f).height(bButton.getHeight() * 2.0f).bottom().padBottom(120);


//        timerTable.add(timeLabel).expandX().padTop(10);

        touchpad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float deltaX = ((Touchpad) actor).getKnobPercentX();
                float deltaY = ((Touchpad) actor).getKnobPercentY();

                Gdx.app.log("Delta X", "" + deltaX);
                Gdx.app.log("Delta Y", "" + deltaY);

                goku.moveBy(deltaX*SPEED,deltaY*SPEED);
                goku.setSpeed(deltaX*SPEED);

                if(goku.isAnimationFinished()){
                    goku.setAnimation(goku.idle);
                }
            }
        });

        aButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goku.resetTime();
                goku.states = Goku.States.PUNCH;
                //if (vegeta.getPosition().dst(goku.getPosition()) < vegeta.getWidth() * 10) {
                if(goku.getPosition().dst(vegeta.getPosition()) < goku.getWidth()*2.5){
                    vegetaHealth = vegetaHealth -0.15f;
                    goku.moveBy(-60,0);
                    vegeta.states = Vegeta.States.DMGED;
                }

            }
        });



        bButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goku.resetTime();
                goku.states = Goku.States.ATTACK;

                gokuAttacks.add(new gokuAttack());
                gokuAttacks.get(iArray).setPosition(goku.getX()+40,goku.getY()+10);

                mainStage.addActor(gokuAttacks.get(iArray));
                iArray++;
                attack1.play();
            }
        });

        //CREATE CHARACTERS
        vegeta = new Vegeta();
        vegeta.setPosition(WIDTH - 250, HEIGHT / 3);
        vegeta.states = Vegeta.States.IDLE;
        mainStage.addActor(vegeta);
        goku = new Goku();
        goku.setPosition(250, HEIGHT / 3);
        goku.states = Goku.States.IDLE;
        mainStage.addActor(goku);

        //vegeta
        vegeta.goku = goku;

        //CREATE UI
        mainStage.addActor(gokuPort);
        mainStage.addActor(vegetaPort);

        mainStage.addActor(countdownLabel);
        mainStage.addActor(redBarGoku);
        mainStage.addActor(gokuHealthBar);
        mainStage.addActor(redBarVegeta);
        mainStage.addActor(vegetaHealthBar);
        firstBool = true;
    }

    @Override
    public void update(float dt) {
        vegeta.act(dt);
        touchpad.act(dt);

        gokuHealthBar.setScale(50*gokuHealth,4f);
        vegetaHealthBar.setScale(50*vegetaHealth,4f);

        //gokuAttackB.moveBy(20,0);

        if(goku.states == Goku.States.ATTACK) {
            if(goku.isAnimationFinished()) {
                goku.states = Goku.States.IDLE;
            }
        }

        if(goku.states == Goku.States.PUNCH) {
            if(goku.isAnimationFinished()) {
                goku.states = Goku.States.IDLE;
            }
        }

        if(vegeta.states == Vegeta.States.ATTACK) {
            if(vegeta.isAnimationFinished()) {
                vegeta.states = Vegeta.States.IDLE;
            }
        }

        //MOVE ATTACKS

        for (gokuAttack gokuAttack : gokuAttacks) {
            gokuAttack.moveBy(20,0);
        }

        for (vegetaAttack vegetaAttack : vegetaAttacks) {
            vegetaAttack.moveBy(-20,0);
        }

        vegeta.coolDownDmg = vegeta.coolDownDmg+dt;

        if(vegeta.coolDownDmg > 1){
            vegeta.playOnce = true;
        }
        else{
            vegeta.playOnce = false;
        }

        goku.preventOverlap(vegeta);

        //cehck collisions
        for (gokuAttack gokuAttack : gokuAttacks) {
            if (gokuAttack.overlaps(vegeta)) {
                gokuAttack.remove();
                vegetaHealth = vegetaHealth - 0.015f;
                vegeta.states = Vegeta.States.DMGED;
            }
        }

        if(gokuHealth<0){
            gokuHealth = 0;
        }

        if(vegetaHealth<0){
            vegetaHealth = 0;
        }

        timeCount += dt;

        if(timeCount >= 1){
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }

        if(goku.getSpeed() != 0){
            goku.setAnimation(goku.walk);
        }

        if(vegeta.getSpeed() != 0){
            vegeta.setAnimation(vegeta.walk);
        }

        if(touchpad.getKnobPercentX() > 0.5 && touchpad.getKnobPercentX() < 0.9) {
            Gdx.app.log("Delta X", "Knob X is " + touchpad.getKnobPercentX());

        }

        if(touchpad.getKnobPercentY() > 0.5 && touchpad.getKnobPercentY() < 0.9) {
            Gdx.app.log("Delta Y", "Knob Y is " + touchpad.getKnobPercentY());
        }

        if ((gokuHealth == 0) || (vegetaHealth == 0)){
            timeUp = true;
        }
/*
        if (vegeta.getPosition().dst(goku.getPosition()) < vegeta.getWidth() * 10) {
            Gdx.app.log("vegeta", "esto ocurre");
            int random = MathUtils.random(5);
            switch (random) {
                case 0:
                    vegeta.states = Vegeta.States.WALK;
                    vegeta.moveBy(-10, -10);

                case 1:
                    vegeta.states = Vegeta.States.WALK;
                    vegeta.moveBy(-10, 10);
                    break;
                case 2:
                    vegeta.states = Vegeta.States.WALK;
                    vegeta.moveBy(-10, 10);
                    break;
                case 3:
                    vegeta.states = Vegeta.States.WALK;
                    vegeta.moveBy(-10, -10);
                    break;
                case 4:
                    vegeta.resetTime();
                    vegeta.states = Vegeta.States.ATTACK;


                    vegetaAttacks.add(new vegetaAttack());
                    vegetaAttacks.get(vArray).setPosition(vegeta.getX()-30,vegeta.getY());

                    //Gdx.app.log("gokuAttacks", gokuAttacks.get(iArray));
                    mainStage.addActor(vegetaAttacks.get(vArray));
                    vArray++;
            }
        }else {

        }*/

        if(isTimeUp()){
            againButton = new TextButton("AGAIN", skin.get(("default"), TextButton.TextButtonStyle.class));
            againButton.setOrigin(Align.center);
            againButton.setTransform(true);
            againButton.setPosition(WIDTH/2-50,HEIGHT/2);
            againButton.setScale(3);
            mainStage.addActor(againButton);

            winnerLabel = new Label("LABEL", labelStyle);
            winnerLabel.setPosition(WIDTH/2-150,20);
            winnerLabel.setScale(1);
            winnerLabel.setFontScale(2);

            if(gokuHealth > vegetaHealth){
                winnerLabel.setText("Goku Won");

                vegeta.remove();

                if(playOnce){
                    vegetaLost.play();
                    playOnce = false;
                }

            } else if(gokuHealth < vegetaHealth){
            winnerLabel.setText("Vegeta Won");

                goku.remove();
                if(playOnce){
                    vegetaLost.play();
                    playOnce = false;
                }
            }
            else{
                winnerLabel.setText("TIE");
            }

            goku.coolDownAttack =  goku.coolDownAttack -dt;

            mainStage.addActor(winnerLabel);

            againButton.addListener(new ActorGestureListener() {
                @Override
                public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);

                        fightMusic.stop();
                        MyGame.menuScreen.menuMusic.play();
                        MyGame.setActiveScreen(MyGame.menuScreen);
                        MyGame.gameScreen.dispose();
                    }
            });
        }

        goku.boundToWorld();
        vegeta.boundToWorld();
    }

    @Override
    public void dispose() {
        super.dispose();

        MyGame.gameScreen = null;

        goku = null;
        winnerLabel = null;
        gokuAttacks = null;
        gokuHealthBar = null;
        vegeta = null;
        uiStage = null;
        gokuPort = null;
        vegetaPort = null;
        countdownLabel = null;
        redBarGoku = null;
        redBarVegeta = null;
        vegetaHealthBar = null;

    }

    public boolean isTimeUp() {
        return timeUp;
    }

}