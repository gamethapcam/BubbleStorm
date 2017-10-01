package com.luarca84.diego.bubblestorm;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by USUARIO on 02/09/2016.
 */
public class Shot {
    Vector2 position;
    Vector2 velocity;
    Viewport viewport;
    int level;
    Player player;
    public Shot(Viewport viewport,int level,Player player) {
        this.viewport = viewport;
        this.level = level;
        this.player = player;
        init();
    }

    public void init() {
        position = new Vector2(player.position.x, player.position.y+Constants.PLAYER_HEIGHT/2);
        velocity = new Vector2(200,0);
    }

    public void update(float delta)
    {
        position.x+=delta*velocity.x;
        position.y+=delta*velocity.y;
    }

    public void render(ShapeRenderer renderer)
    {
        renderer.setColor(Color.WHITE);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.rect(position.x, position.y,Constants.PLAYER_WIDTH,Constants.PLAYER_WIDTH);
    }
}
