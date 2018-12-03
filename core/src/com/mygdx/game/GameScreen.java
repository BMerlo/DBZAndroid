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
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
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
import java.util.Timer;
import java.util.TimerTask;


public class GameScreen extends ScreenBeta {

    Music fightMusic;
    Vegeta vegeta;
    Goku goku;

    Sound attack1;
    Sound clash;
    Sound vegetaLost;
    Sound gokuLost;

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

    float vegetaHealth;//0 = dead, 1 = full health
    float gokuHealth;//0 = dead, 1 = full health

    TextButton againButton;

    boolean playOnce;

    ArrayList<gokuAttack> gokuAttacks;
    ArrayList<vegetaAttack> vegetaAttacks;
    int iArray;
    int vArray;

    MoveByAction moveByAction1;

    float AItimer;

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
        playOnce = true;
        AItimer = 2;

        MoveByAction moveByAction1 = new MoveByAction();
        moveByAction1.setDuration(2);
        moveByAction1.setAmountY(2.0f);

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
        fightMusic.setVolume(0.5f);
        fightMusic.play();

        attack1 = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/attack.wav"));
        vegetaLost = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/vegetaDies.mp3"));
        gokuLost = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/gokuDies.mp3"));
        clash = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/clash.wav"));

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


        uiTable.add(touchpad).width(touchpad.getWidth() * 1.5f).height(touchpad.getHeight() * 1.5f).padRight(800).padTop(600);


        Button aButton = new Button(uiSkin, "red");
        Button bButton = new Button(uiSkin, "blue");
        aButton.getColor().a = 1.0f;
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
                if(goku.playOnceAttack) {
                    goku.resetTime();
                    goku.states = Goku.States.PUNCH;
                    //if (vegeta.getPosition().dst(goku.getPosition()) < vegeta.getWidth() * 10) {
                    if (goku.getPosition().dst(vegeta.getPosition()) < goku.getWidth() * 2.5) {
                        goku.moveBy(-60, 0);
                        vegeta.states = Vegeta.States.DMGED;
                    }
                }
            }
        });



        bButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(goku.playOnceAttack) {
                    goku.resetTime();
                    goku.states = Goku.States.ATTACK;

                    gokuAttacks.add(new gokuAttack());
                    gokuAttacks.get(iArray).setPosition(goku.getX() + 50, goku.getY() + 20);

                    mainStage.addActor(gokuAttacks.get(iArray));
                    iArray++;
                    attack1.play();
                }
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


        //CREATE UI
        mainStage.addActor(gokuPort);
        mainStage.addActor(vegetaPort);

        mainStage.addActor(countdownLabel);
        mainStage.addActor(redBarGoku);
        mainStage.addActor(gokuHealthBar);
        mainStage.addActor(redBarVegeta);
        mainStage.addActor(vegetaHealthBar);
    }

    @Override
    public void update(float dt) {
        vegeta.act(dt);
        touchpad.act(dt);

        vegetaHealth = vegeta.health;
        gokuHealth = goku.health;

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

        if(vegeta.states == Vegeta.States.PUNCH) {
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

        //check collisions
        for (gokuAttack gokuAttack : gokuAttacks) {
            if (gokuAttack.overlaps(vegeta)) {
                gokuAttack.remove();
                vegeta.states = Vegeta.States.DMGED;
            }
        }

        for (vegetaAttack vegetaAttack : vegetaAttacks) {
            if (vegetaAttack.overlaps(goku)) {
                vegetaAttack.remove();
                goku.states = Goku.States.DMGED;
            }
        }

        //CHECK COOLDOWNS
        vegeta.coolDownDmg = vegeta.coolDownDmg+dt;
        vegeta.coolDownAttack = vegeta.coolDownAttack+dt;

        goku.coolDownDmg =  goku.coolDownDmg +dt;
        goku.coolDownAttack =  goku.coolDownAttack +dt;


        if(vegeta.coolDownDmg > 1){
            vegeta.playOnce = true;
        }
        else{
            vegeta.playOnce = false;
        }

        if(goku.coolDownDmg > 1){
            goku.playOnce = true;
        }
        else{
            goku.playOnce = false;
        }

        if(vegeta.coolDownAttack > 2){
            vegeta.playOnceAttack = true;
        }
        else{
            vegeta.playOnceAttack = false;
        }

        if(goku.coolDownAttack > 1.0f){
            goku.playOnceAttack = true;
        }
        else{
            goku.playOnceAttack = false;
        }

        //can't overlaps players
        goku.preventOverlap(vegeta);
        vegeta.preventOverlap(goku);

        //SAFE HEALTHBARS
        if(goku.health<0){
            goku.health = 0;
        }

        if(vegeta.health<0){
            vegeta.health = 0;
        }

        //TIMER CONTROLLER
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

        if ((goku.health == 0) || (vegeta.health == 0)){
            timeUp = true;
        }

        AItimer = AItimer + dt;

        if(vegeta.health != 0 && goku.health != 0) {
            if (AItimer > 3) {
                int random = MathUtils.random(6);
                Gdx.app.log("random", String.valueOf(random));
                switch (random) {
                        case 0:
                            vegeta.states = Vegeta.States.WALK;
                            vegeta.moveBy(0, -10);
                            if(vegeta.getSpeed() != 0)
                                AItimer = 0;

                        break;
                    case 1:
                        vegeta.states = Vegeta.States.WALK;
                        vegeta.moveBy(0, 10);
                        if(vegeta.getSpeed() != 0)
                            AItimer = 0;

                        break;
                    case 2:
                        vegeta.states = Vegeta.States.WALK;
                        vegeta.moveBy(0, -10);
                        if(vegeta.getSpeed() != 0)
                            AItimer = 0;

                        break;
                    case 3:
                        vegeta.states = Vegeta.States.WALK;
                        vegeta.moveBy(0, -10);
                        if(vegeta.getSpeed() != 0)
                            AItimer = 0;

                        break;
                    case 4:
                        if (vegeta.playOnceAttack) {
                            vegeta.resetTime();
                            vegeta.states = Vegeta.States.ATTACK;
                            attack1.play();

                            vegetaAttacks.add(new vegetaAttack());
                            vegetaAttacks.get(vArray).setPosition(vegeta.getX() - 30, vegeta.getY() + 20);

                            mainStage.addActor(vegetaAttacks.get(vArray));
                            vArray++;
                            AItimer = 0;
                        }
                        if(!vegeta.playOnceAttack){
                            AItimer = 0;
                        }
                        break;
                    case 5:
                        vegeta.resetTime();
                        vegeta.states = Vegeta.States.BLOCK;
                        AItimer = 0;
                        break;

                    case 6:
                        vegeta.states = Vegeta.States.WALK;
                        vegeta.moveBy(0, 10);
                        if(vegeta.getSpeed() != 0)
                            AItimer = 0;
                        break;

                    default:
                        vegeta.moveBy(-20,0);
                        break;
                }
            }
        }
        /*}else {

        }*/

        //SIMPLE - RANDOM AI Follow
        int random2 = MathUtils.random(150);
        if(random2 >= 0 && random2 <10) {
            if (goku.getY() > vegeta.getY()) {
                vegeta.moveBy(0, 10);
            }
        }
        else if(random2 >= 140 && random2 <150) {
        if (goku.getY() < vegeta.getY()) {
            vegeta.moveBy(0, -10);
        }
        else{
        }
            vegeta.moveBy(0, 0);
        }

        if(isTimeUp()){
            againButton = new TextButton("AGAIN", skin.get(("default"), TextButton.TextButtonStyle.class));
            againButton.setOrigin(Align.center);
            againButton.setTransform(true);
            againButton.setPosition(WIDTH/2-50,HEIGHT/2);
            againButton.setScale(3);
            mainStage.addActor(againButton);

            winnerLabel = new Label("WINNER", labelStyle);
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
            }else if(gokuHealth == vegetaHealth){
                winnerLabel.setText("TIE");
                vegeta.remove();
                goku.remove();
                goku.health = 0;
                vegeta.health = 0;
                }
            else{
                goku.remove();
                goku.health = 0;
                vegeta.health = 0;
                vegeta.remove();
                winnerLabel.setText("TIE");
            }



            mainStage.addActor(winnerLabel);

            //remove controls
            uiTable.remove();
            countdownLabel.remove();

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
        vegeta = null;
        winnerLabel = null;
        againButton = null;
        gokuAttacks = null;
        vegetaAttacks = null;
        uiStage = null;
        gokuPort = null;
        vegetaPort = null;
        countdownLabel = null;
        redBarGoku = null;
        redBarVegeta = null;
        vegetaHealthBar = null;
        gokuHealthBar = null;
        countdownLabel = null;

    }

    public boolean isTimeUp() {
        return timeUp;
    }

}
