package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * Created by markapptist on 2018-11-16.
 */

public class SetTextAction extends Action {

    protected String textToDisplay;

    public SetTextAction(String s) {
        textToDisplay = s;
    }

    public boolean act(float dt) {
        DialogBox db = (DialogBox)target;
        db.setText(textToDisplay);
        return true; //action completed
    }
}
