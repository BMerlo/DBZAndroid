package com.mygdx.game;

public class vegetaAttack extends ActorBeta {

    public vegetaAttack(){

        //Texture ballText = new Texture("sprites/goku/ballGoku.png");
        loadTexture("sprites/vegeta/ballVegeta.png");
        //setTexture(ballText);

        this.setBoundaryRectangle();
        moveBy(-20,0);

    }

    @Override
    public void act(float dt) {
        super.act(dt);
    }

}

