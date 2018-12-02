package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    ActorBeta attackBall;

    Goku goku;

    Stage game;
    //ScreenBeta game;

    Vegeta() {
        playOnce = false;
        coolDownDmg = 0;

        String[] idleString = {"sprites/vegeta/2.png", "sprites/vegeta/3.png",
                "sprites/vegeta/4.png", "sprites/vegeta/5.png"};

        String[] moveString = {"sprites/vegeta/walk0.png", "sprites/vegeta/walk1.png"};

        String[] punchString = {"sprites/vegeta/attack0.png", "sprites/vegeta/attack1.png",
                "sprites/vegeta/attack2.png", "sprites/vegeta/attack3.png",
                "sprites/vegeta/attack4.png", };

        String[] attack2String = {"sprites/vegeta/attack20.png", "sprites/vegeta/attack21.png", "sprites/vegeta/attack22.png",
                "sprites/vegeta/attack23.png", "sprites/vegeta/attack24.png", "sprites/vegeta/attack25.png",
                "sprites/vegeta/attack26.png", "sprites/vegeta/attack27.png"};

        idle = loadAnimationFromFiles(idleString, 0.5f, true);
        walk = loadAnimationFromFiles(moveString, 0.5f, false);
        attack1 = loadAnimationFromFiles(punchString, 0.2f, true); //punch
        attack2 = loadAnimationFromFiles(attack2String, 0.2f, true); //blast

        this.setBoundaryRectangle();

        Damaged = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/vegetaAh.mp3"));
        setScale(2.0f);

        setMaxSpeed(900);
    }



    @Override
    public void act(float dt) {
        super.act(dt);

        setAcceleration(900);
        accelerateAtAngle(270);

        switch (states) {
            case IDLE:
                this.setAnimation(attack2);
                break;

            case ATTACK:
                this.setAnimation(attack2);
                //this.setAnimation(attack2);
                break;

            case PUNCH:
                this.setAnimation(attack1);
                //this.setAnimation(attack1);
                break;

            case WALK:
                this.setAnimation(walk);
                break;

            case DMGED:
                if(playOnce) {
                    Damaged.play();
                    moveBy(2,0);
                    coolDownDmg = 0;
                }
                this.states = Vegeta.States.IDLE;
                break;

            default:
                Gdx.app.log("States", "Vegeta changing states");
                break;
        }



       //  applyPhysics(dt);
    }
}
