package com.test.mybackend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractScreen implements Screen {
	private SpriteBatch batch;
	private Texture background;
	private float width, height;

	@Override
	public void render(float delta) {
		update(delta);

		batch.begin();
		renderBackground(batch);
		renderObjects(batch);
		renderGui(batch);
		batch.end();

	}

	private void renderBackground(SpriteBatch batch) {
		if (background != null)
			batch.draw(background, 0, 0, width, height);
	}

	// update gets called every frame..could have done it through render but
	// easier to understand this way
	public abstract void update(float deltaTime);

	// renders foreground objects first
	public abstract void renderObjects(SpriteBatch batch);

	// renders gui objects second so gui is on top of players
	public abstract void renderGui(SpriteBatch batch);

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		
/*		camera = new OrthographicCamera(width, height);
		camera.setToOrtho(true);
		camera.translate(-width/2f, -height/2f);
		camera.update();*/
		
		/*
		 * mainCamera = new OrthographicCamera(width, height);
		 * mainCamera.translate(width/2f, height/2f); mainCamera.zoom = 2f;
		 * mainCamera.update(); System.out.println(mainCamera.position);
		 */

		abstractScreenInit();
	}

	public abstract void abstractScreenInit();

	@Override
	public abstract void hide();

	@Override
	public abstract void pause();

	@Override
	public abstract void resume();

	@Override
	public void dispose() {
		batch.dispose();
	}

	// handles common inputs that I usually use every game
	public abstract boolean handleTouchInput(int screenx, int screeny,
			int pointer, int button);

	public abstract boolean handleKeyInput(int keycode);

	public abstract boolean handleKeyUp(int keycode);

	public abstract boolean handleTouchInputUp(int screenx, int screeny,
			int pointer, int button);

	public abstract boolean handleDrag(int screeny, int screenx, int pointer);

	public void setBackgroundImage(Texture newBackground) {
		this.background = newBackground;
	}
}
