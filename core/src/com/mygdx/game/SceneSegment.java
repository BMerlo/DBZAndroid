package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by markapptist on 2018-11-16.
 */

public class SceneSegment {

    private ActorBeta actor;
    private Action action;

    public SceneSegment(ActorBeta a1, Action a2) {
        actor = a1;
        action = a2;
    }

    public void start() {
        actor.clearActions();
        actor.addAction(action);
    }

    public boolean isFinished() {
        return (actor.getActions().size == 0);
    }

    public void finish() {
        if(actor.hasActions())
            actor.getActions().first().act(1000000);

        actor.clearActions();
    }
}
