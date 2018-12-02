package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

/**
 * Created by markapptist on 2018-11-12.
 */

public class MenuScreen extends ScreenBeta {
    Music menuMusic;

    TextButton startButton;
    TextButton exitButton;

    public Sound selectionSfx;

    Label label;

    /**PARTICLE EFFECTS**/
    FireParticle fire;
    LogoDBZ logo;


    @Override
    public void initialize() {

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/menu.mp3"));
        menuMusic.setLooping(true);

        uiTable.background(skin.getDrawable("window-c"));


        menuMusic.setVolume(0.0f);
        menuMusic.play();

        logo = new LogoDBZ();
        logo.setPosition(WIDTH / 2-200, 750 );


        selectionSfx = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/selectionSfx.mp3"));
        long id = selectionSfx.play();
        selectionSfx.setVolume(id,0.5f);

        uiStage.addActor(tableContainer);
        mainStage.addActor(logo);

        startButton = new TextButton("Start", skin.get(("default"), TextButton.TextButtonStyle.class));
        startButton.setOrigin(Align.center);
        startButton.setTransform(true);
        startButton.setScale(3);



        exitButton = new TextButton("Exit", skin.get(("default"), TextButton.TextButtonStyle.class));
        exitButton.setOrigin(Align.center);
        exitButton.setTransform(true);
        exitButton.setScale(3);

        setUpButtons();

        label = new Label("LABEL", labelStyle);

        //Add to TABLE

        uiTable.row().padTop(HEIGHT / 12).padBottom(HEIGHT / 12);
        uiTable.add(startButton).size(startButton.getWidth(), startButton.getHeight()).expandX();



        uiTable.row().padTop(HEIGHT / 12).padBottom(HEIGHT / 12);
        uiTable.add(exitButton).size(exitButton.getWidth(), exitButton.getHeight()).expandX();

        /**PARTICLE EFFECTS**/
        fire = new FireParticle();
        fire.centerAtActor(startButton);
        fire.start();
        fire.setPosition(WIDTH / 2, HEIGHT / 2);
        fire.setScale(2.0f);


        mainStage.addActor(fire);


    }

    public void setUpButtons() {

        startButton.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                selectionSfx.play();
                menuMusic.stop();

  /*              if(MyGame.dialogScreen == null) {
                    MyGame.dialogScreen = new DialogScreen();
                    MyGame.setActiveScreen(MyGame.dialogScreen);
                }
                else if(MyGame.dialogScreen != null) {
                    MyGame.gameScreen = new GameScreen();
                    MyGame.setActiveScreen(MyGame.gameScreen);
                }
*/
                if(MyGame.gameScreen == null) {
                    MyGame.gameScreen = new GameScreen();
                    MyGame.setActiveScreen(MyGame.gameScreen);
                }
                else{
                    MyGame.dialogScreen.initialize();
                    MyGame.setActiveScreen(MyGame.dialogScreen);
                    MyGame.gameScreen.initialize();
                    MyGame.setActiveScreen(MyGame.gameScreen);
                }
            }
        });
    }

    @Override
    public void render(float delta) {super.render(delta);}

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void update(float dt) {fire.act(dt);}

    public void dispose(){

        MyGame.menuScreen = null;
        exitButton = null;
        startButton = null;
        fire = null;
        logo = null;
        uiStage = null;
        uiTable = null;
    }
}
