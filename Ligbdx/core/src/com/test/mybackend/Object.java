package com.test.mybackend;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Object {
	Texture image;
	Rectangle bounds;
	
	public Object(){
		init();
	}
	
	public Object(float x, float y, float width, float height) {
		init();
		bounds.set(x, y, width, height);
	}
	
	private void init(){
		image = new Texture("badlogic.jpg");
		bounds = new Rectangle();
	}
	
	public void setImage(Texture image){
		this.image = image;
	}

	public abstract void update(float deltaTime);
	
	public void render(SpriteBatch batch){
		if(image != null){
			batch.draw(image, bounds.x, bounds.y, bounds.width, bounds.height);
		}
	}
}
