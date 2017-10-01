package com.luarca84.diego.bubblestorm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

/**
 * Created by USUARIO on 02/09/2016.
 */
public class PlayScreen extends InputAdapter implements Screen {
    BubbleStormGame game;
    SpriteBatch batch;
    BitmapFont font;
    FitViewport viewport;
    ShapeRenderer renderer;

    ArrayList<Ball> ballArrayList;
    ArrayList<Shot> shotArrayList;
    Player player;
    int level;

    public PlayScreen(BubbleStormGame game,int level)
    {
        this.game = game;
        this.level = level;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        viewport = new FitViewport(Constants.DIFFICULTY_WORLD_SIZE_WIDTH, Constants.DIFFICULTY_WORLD_SIZE_HEIGHT);
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        ballArrayList = new ArrayList<Ball>();
        for(int i=0; i<100;i++)
            ballArrayList.add(new Ball(viewport, level));
        shotArrayList = new ArrayList<Shot>();
        player = new Player(viewport);

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        if(player.lives <= 0)
        {
            game.ShowStatusScreen(false,level);
        }
        else if(ballArrayList.size() == 0)
        {
            game.ShowStatusScreen(true,level);
        }
        else
        {

            for(int i=0; i< ballArrayList.size();i++)
                ballArrayList.get(i).update(delta,player);
            for(int i=0; i< shotArrayList.size();i++)
                shotArrayList.get(i).update(delta);

            player.update(delta);
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            viewport.apply();
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin();
            float width = viewport.getWorldWidth();
            float height = viewport.getWorldHeight();
            font.setColor(Color.WHITE);
            font.draw(batch, "Lives: " + player.lives +"  Balls: "+ballArrayList.size(), width / 2 - 100, height - 20);
            font.draw(batch, "Level: " + level , width / 2 - 200, height - 20);
            font.setColor(Color.CYAN);
            font.draw(batch, "UP", 40, viewport.getWorldHeight() - 40);
            font.draw(batch, "HIT", 40, viewport.getWorldHeight()/2 );
            font.draw(batch, "DOWN", 40, 60);
            batch.end();

            renderer.setProjectionMatrix(viewport.getCamera().combined);
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.CYAN);
            renderer.circle(50, 50, 49, 20);
            renderer.circle(50, viewport.getWorldHeight() - 50, 49, 20);
            renderer.circle(50, viewport.getWorldHeight()/2 , 49, 20);
            renderer.rect(100, 1, viewport.getWorldWidth() - 100, viewport.getWorldHeight() - 2);
            renderer.set(ShapeRenderer.ShapeType.Filled);

            for(int i=0; i< ballArrayList.size();i++)
                ballArrayList.get(i).render(renderer);
            for(int i=0; i< shotArrayList.size();i++)
                shotArrayList.get(i).render(renderer);
            player.render(renderer);

            renderer.end();

            for(int i=shotArrayList.size()-1; i>=0 ;i--)
            {
                if(shotArrayList.get(i).position.x > width)
                    shotArrayList.remove(i);
            }

            for(int i=0; i< shotArrayList.size();i++)
            {
                Shot shot = shotArrayList.get(i);
                Rectangle rectangleShot = new Rectangle(shot.position.x,shot.position.y,Constants.PLAYER_WIDTH,Constants.PLAYER_WIDTH);
                for(int j = ballArrayList.size()-1; j>=0; j--)
                {
                    Ball ball = ballArrayList.get(j);
                    Rectangle circle = new Rectangle(ball.position.x,ball.position.y,Constants.BALL_RADIUS,Constants.BALL_RADIUS);
                    if(circle.overlaps(rectangleShot))
                    {
                        ballArrayList.remove(j);
                    }
                }

            }

            if (Gdx.input.isTouched()) {
                //HIT
                Shot shot = new Shot(viewport, level, player);
                shot.init();
                shotArrayList.add(shot);

            }


        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

        for(int i=0; i< ballArrayList.size();i++)
            ballArrayList.get(i).init();
        for(int i=0; i< shotArrayList.size();i++)
            shotArrayList.get(i).init();
        player.init();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            game.ShowMenuScreen();
        }
        return false;
    }
}

