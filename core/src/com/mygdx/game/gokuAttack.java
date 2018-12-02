package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class gokuAttack extends ActorBeta {

    public gokuAttack(){

        //Texture ballText = new Texture("sprites/goku/ballGoku.png");
        loadTexture("sprites/goku/ballGoku.png");
        //setTexture(ballText);

        this.setBoundaryRectangle();

        //moveBy(20,0);

    }

    @Override
    public void act(float dt) {
        super.act(dt);
        /*if (this.overlaps(Vegeta))
            remove = true;*/
    }

}
