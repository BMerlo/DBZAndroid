package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Goku extends Fighter{

    public enum States {
            IDLE, ATTACK, PUNCH, WALK
    }

    States states;

    float coolDownAttack;

    Goku() {
        String[] idleString = {
                "sprites/goku/4.png", "sprites/goku/5.png", "sprites/goku/6.png", "sprites/goku/7.png"};

        String[] moveString = {"sprites/goku/walk0.png", "sprites/goku/walk1.png"};

        String[] attack1String = {"sprites/goku/attack10.png", "sprites/goku/attack11.png", "sprites/goku/attack12.png", "sprites/goku/attack13.png"};

        String[] attack2String = {"sprites/goku/attack20.png", "sprites/goku/attack21.png", "sprites/goku/attack22.png", "sprites/goku/attack25.png"};

        //"sprites/goku/attack23.png",
        //                "sprites/goku/attack24.png"

        idle = loadAnimationFromFiles(idleString, 0.5f, true);
        walk = loadAnimationFromFiles(moveString, 0.5f, false);
        attack1 = loadAnimationFromFiles(attack1String, 0.1f, false);
        attack2 = loadAnimationFromFiles(attack2String, 0.1f, false);

        this.setBoundaryRectangle();

        setScale(2.0f);

        setMaxSpeed(900);
    }


    @Override
    public void act(float dt) {
        super.act(dt);

        setAcceleration(1500);
        accelerateAtAngle(270);
        //  applyPhysics(dt);

        switch (states) {
            case IDLE:
                this.setAnimation(idle);
                break;

            case PUNCH:
                this.setAnimation(attack1);
                break;

            case ATTACK:
                this.setAnimation(attack2);
                break;

            case WALK:
                this.setAnimation(walk);
                break;

            default:
                Gdx.app.log("States", "Goku default state");
                break;
        }
    }
}
