package com.luarca84.diego.bubblestorm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by USUARIO on 02/09/2016.
 */
public class StatusScreen extends InputAdapter implements Screen {
    BubbleStormGame game;
    SpriteBatch batch;
    BitmapFont font;
    FitViewport viewport;
    ShapeRenderer renderer;
    boolean victory;
    int level;

    public StatusScreen(BubbleStormGame game, boolean victory,int level)
    {
        this.game = game;
        this.victory = victory;
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


        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();
        font.setColor(Color.WHITE);
        if(victory){
            font.draw(batch,"Victory Level "+level+" (Touch the screen)",width/2-100,height/2);
        }
        else
        {
            font.draw(batch,"Game Over Level "+level+" (Touch the screen)",width/2-100,height/2);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        if(!victory || ( victory&&level==5))
            game.ShowMenuScreen();
        else
            game.ShowPlayScreen(level+1);
        return true;
    }
}
