package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Vegeta extends Fighter {
    public enum States {
        IDLE, ATTACK, PUNCH, WALK, DMGED
    }

    Sound Damaged;

    States states;

    boolean playOnce;

    float coolDownDmg;

    Vegeta() {


        String[] idleString = {"sprites/vegeta/2.png", "sprites/vegeta/3.png",
                "sprites/vegeta/4.png", "sprites/vegeta/5.png"};

        String[] moveString = {"sprites/vegeta/walk0.png", "sprites/vegeta/walk1.png"};

        idle = loadAnimationFromFiles(idleString, 0.5f, true);
        walk = loadAnimationFromFiles(moveString, 0.5f, true);

        this.setBoundaryRectangle();
        Damaged = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/vegetaAh.mp3"));
        setScale(2.0f);

        MoveByAction moveByAction1 = new MoveByAction();
        moveByAction1.setAmountY(55.0f);

        MoveByAction moveByAction2 = new MoveByAction();
        moveByAction2.setAmountY(-55.0f);

        SequenceAction sequenceAction = new SequenceAction(moveByAction1, moveByAction2);
        this.addAction(sequenceAction);

        setMaxSpeed(900);

    }

    @Override
    public void act(float dt) {
        super.act(dt);

        setAcceleration(900);
        accelerateAtAngle(270);

        switch (states) {
            case IDLE:
                this.setAnimation(idle);

                break;

            case PUNCH:
                this.setAnimation(attack2);
                break;

            case ATTACK:
                this.setAnimation(attack1);
                break;

            case WALK:
                this.setAnimation(walk);
                break;
            case DMGED:
                    Damaged.play();
                this.states = Vegeta.States.IDLE;
                break;

            default:
                Gdx.app.log("States", "Vegeta changing states");
                break;
        }

       //  applyPhysics(dt);
    }
}
