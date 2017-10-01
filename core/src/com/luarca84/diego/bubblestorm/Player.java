package com.luarca84.diego.bubblestorm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by USUARIO on 02/09/2016.
 */
public class Player {
    Vector2 position;
    Vector2 velocity;
    Viewport viewport;
    int lives;

    public Player(Viewport viewport) {
        this.viewport = viewport;
        this.lives = 10;
        init();
    }

    public void init() {
        position = new Vector2(100, viewport.getWorldHeight()/2);
        velocity = new Vector2(0,200);
    }

    public void update(float delta)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += delta * velocity.y;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= delta * velocity.y;
        }

        Vector2 touchPosition = new Vector2(0,0);

        if (Gdx.input.isTouched()){
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY());

            if( touchPosition.y < viewport.getScreenHeight()/3)
                position.y += delta * velocity.y;
            else if( touchPosition.y >2* viewport.getScreenHeight()/3)
                position.y -= delta * velocity.y;
        }

        ensureInBounds();
    }

    private void ensureInBounds() {

        if (position.y < 0) {
            position.y = 0;
        }
        if (position.y + Constants.PLAYER_HEIGHT > viewport.getWorldHeight()) {
            position.y = viewport.getWorldHeight() - Constants.PLAYER_HEIGHT;
        }
    }

    public void render(ShapeRenderer renderer)
    {
        renderer.setColor(Color.WHITE);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.rect(position.x,position.y,Constants.PLAYER_WIDTH,Constants.PLAYER_HEIGHT);
        renderer.rect(position.x,position.y + Constants.PLAYER_HEIGHT/2 - Constants.PLAYER_WIDTH,Constants.PLAYER_WIDTH*2,Constants.PLAYER_WIDTH*2);
    }
}
