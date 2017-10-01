package com.luarca84.diego.bubblestorm;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by USUARIO on 02/09/2016.
 */
public class Ball {
    Vector2 position;
    Vector2 velocity;
    Viewport viewport;
    int level;
    Color color = Color.WHITE;
    public Ball(Viewport viewport,int level) {
        this.viewport = viewport;
        this.level = level;
        init();
    }

    public void init() {
        Random random = new Random();
        int randomX = random.nextInt((int)viewport.getWorldWidth()/3);
        int randomY = random.nextInt((int)viewport.getWorldHeight()/3);
        position = new Vector2(viewport.getWorldWidth() / 2+randomX, viewport.getWorldHeight()/2+randomY);
        velocity = new Vector2(200,100);
        switch (level)
        {
            case 1:velocity = new Vector2(200,100);
                break;
            case 2:velocity = new Vector2(220,120);
                break;
            case 3:velocity = new Vector2(240,140);
                break;
            case 4:velocity = new Vector2(260,160);
                break;
            case 5:velocity = new Vector2(280,180);
                break;

        }

        if(random.nextBoolean())
            velocity.x = -velocity.x;
        if(random.nextBoolean())
            velocity.y = -velocity.y;

        int indexColor = random.nextInt(5);
        switch (indexColor)
        {
            case 0: color = Color.ORANGE;
                break;
            case 1: color = Color.BLUE;
                break;
            case 2: color = Color.GREEN;
                break;
            case 3: color = Color.LIGHT_GRAY;
                break;
            case 4: color = Color.RED;
                break;
        }
    }

    public void update(float delta, Player player)
    {
        Rectangle rectangleBall = new Rectangle(position.x,position.y,Constants.BALL_RADIUS,Constants.BALL_RADIUS);
        Rectangle rectanglePlayer = new Rectangle(player.position.x,player.position.y,Constants.PLAYER_WIDTH,Constants.PLAYER_HEIGHT);

        if(position.y-Constants.BALL_RADIUS<0)
        {
            velocity.y = - velocity.y;
            position.y++;
        }
        else if(position.y+Constants.BALL_RADIUS > viewport.getWorldHeight())
        {
            velocity.y = -velocity.y;
            position.y--;
        }
        else if(position.x-Constants.BALL_RADIUS <100 - Constants.PLAYER_WIDTH)
        {
            velocity.x = -velocity.x;
            position.x++;
        }
        else if(position.x+Constants.BALL_RADIUS > viewport.getWorldWidth())
        {
            velocity.x = -velocity.x;
            position.x--;
        }
        else if(rectangleBall.overlaps(rectanglePlayer)) {
            player.lives--;
            velocity.x = -velocity.x;
            position.x++;
        }

        position.x+=delta*velocity.x;
        position.y+=delta*velocity.y;


    }

    public void render(ShapeRenderer renderer)
    {
        renderer.setColor(color);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.circle(position.x, position.y,Constants.BALL_RADIUS, 20);

    }
}
