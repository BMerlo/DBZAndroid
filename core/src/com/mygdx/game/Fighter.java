package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Fighter extends ActorBeta {

    Animation<TextureRegion> idle;
    Animation<TextureRegion> walk;
    Animation<TextureRegion> attack1;
    Animation<TextureRegion> attack2;
    Animation<TextureRegion> block;

    Fighter() {

    }

    @Override
    public void act(float dt) {
        super.act(dt);

         setAcceleration(900);
         accelerateAtAngle(270);
        // applyPhysics(dt);
    }
}
