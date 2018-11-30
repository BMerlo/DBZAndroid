package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by markapptist on 2018-10-16.
 */

public abstract class ScreenBeta implements Screen, InputProcessor {

    protected Stage mainStage;
    protected Stage dialogStage;

    //UI

    //STAGE
    protected Stage uiStage;

    //TABLE TO ORGANIZE LAYOUT
    Table uiTable;
    Container<Table> tableContainer;

    //LABEL
    LabelStyle labelStyle;

    //BUTTON
    ButtonStyle buttonStyle;

    //SOUNDS
    Music defaultBackgroundMusic;
    Sound defaultSoundEffect;

    //SKINS
    Skin skin;

    //BOOLEANS
    boolean isPaused;

    int score;

    static int WIDTH = Gdx.graphics.getWidth();
    static int HEIGHT = Gdx.graphics.getHeight();

    //CONSTRUCTOR
    ScreenBeta()
    {

    skin = new Skin(Gdx.files.internal("skins/quantum-horizon/skin/quantum-horizon-ui.json"));
//        skin = new Skin(Gdx.files.internal("skins/orange/skin/uiskin.json"));

        isPaused = false;

        mainStage = new Stage();
        uiStage = new Stage();

        /***
         * TODO: USE THE TABLE BELOW TO SET THE BUTTONS ON BOTH START SCREEN AND GAME SCREEN
         */

        tableContainer = new Container<Table>();

        float cw = WIDTH * 0.5f;
        float ch = HEIGHT * 1.0f;

        tableContainer.setSize(WIDTH / 3, HEIGHT);
        tableContainer.setPosition((WIDTH - cw) - (tableContainer.getWidth() / 2), (HEIGHT - ch) / 2.0f);
        tableContainer.fillX();
        tableContainer.setDebug(true);

        uiTable = new Table();

        uiTable.setSize(WIDTH, HEIGHT);
        uiTable.setBounds(0, 0, Gdx.graphics.getWidth() , Gdx.graphics.getHeight());

        tableContainer.setActor(uiTable);


        //INITIALIZE A DEFAULT BUTTON
        buttonStyle = new ButtonStyle(skin.getDrawable("button-c"), skin.getDrawable("button-pressed-c"), skin.getDrawable("button-over-pressed-c"));

        //INITIALIZE A LABEL
        labelStyle = new LabelStyle(skin.get(("title"), Label.LabelStyle.class));

        initialize();


    }

    public abstract void initialize();

    /**
     *  Called when this becomes the active screen in a Game.
     *  Set up InputMultiplexer here, in case screen is reactivated at a later time.
     */
    @Override
    public void show() {

        //GET the InputMultiplexer
        InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();

        //Add InputProcessor to the screen
        im.addProcessor(this);

        //Add InputProcessor to the stage
        im.addProcessor(mainStage);
        im.addProcessor(uiStage);
    }

    /**
     *  Called when this is no longer the active screen in a Game.
     *  Screen class and Stages no longer process input.
     *  Other InputProcessors must be removed manually.
     */
    @Override
    public void hide() {

        //Get InputProcessor
        InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();

        //Remove InputProcessor
        im.removeProcessor(this);
        im.removeProcessor(mainStage);
        im.removeProcessor(uiStage);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public abstract void update(float dt);

    @Override
    public void render(float delta) {

        score++;

        //PAUSE LOGIC
        if(isPaused)
            delta = 0;
        else {
            delta = Math.min(delta, 1/30.0f);
        }

        mainStage.act(delta);
        uiStage.act(delta);

        update(delta);
        mainStage.setDebugAll(true);

        Gdx.gl.glClearColor(0.5f, 0f, 0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainStage.draw();
        uiStage.draw();

        uiTable.setDebug(true);
    }

    public boolean isTouchDownEvent(Event e) {
        return (e instanceof InputEvent) && ((InputEvent)e).getType().equals(InputEvent.Type.touchDown);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
