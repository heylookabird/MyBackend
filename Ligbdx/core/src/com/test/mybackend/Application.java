package com.test.mybackend;

import imagegallery.ImageGallery;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Application extends Game{
	public static Application myApplication = new Application();
	public static AbstractScreen thisScreen = new ImageGallery();// = new TestScreen();
	public int level = 1;
	@Override
	public void create() {		
		this.changeScreen(thisScreen);
		InputManager.inputManager.init();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(255f,255f,255f,255f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		thisScreen.render(.05f);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

	}
	
	public void changeScreen(AbstractScreen screen){
		((Game) Gdx.app.getApplicationListener()).setScreen(thisScreen);
		//this.setScreen(screen);
		thisScreen = screen;
	}
}
