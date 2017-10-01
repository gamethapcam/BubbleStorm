package com.luarca84.diego.bubblestorm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BubbleStormGame extends Game {


	@Override
	public void create() {
		ShowMenuScreen();
	}

	public void ShowMenuScreen(){setScreen(new MenuScreen(this));}
	public void ShowPlayScreen(int level){setScreen(new PlayScreen(this,level));}
	public void ShowStatusScreen(boolean victory,int level){setScreen(new StatusScreen(this,victory,level));}


}
