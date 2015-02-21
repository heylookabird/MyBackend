package imagegallery;
/*
 * File: Button.java
 * By: Harjit Randhawa
 * Date: 2/17/15
 * 
 * 
 * Description: Abstract button class that allows to move position, set the size
 * but asks the children to specify what they want to run when they are actually pressed
 * Passes in the parent(Singleton) gallery so that it can run it's actions
 */
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Button {
	protected ImageGallery gallery;
	protected Rectangle bounds;
	protected Texture image;
	
	public Button(ImageGallery parent, int x, int y, int width, int height, String path){
		bounds = new Rectangle(x, y, width, height);
		image = new Texture(path);
		gallery = parent;
		resize();
	}
	
	public Button(ImageGallery parent, String path){
		image = new Texture(path);
		bounds = new Rectangle();
		gallery = parent;
		resize();

	}
	
	public void resize(){
		float w = image.getWidth();
		float h = image.getHeight();
		
		while(w > gallery.getSectionWidth() || h > gallery.getSectionHeight()){
			w = w * .95f;
			h = h * .95f;
		}
		
		setSize(w,h);
	}
	public float getWidth(){
		return bounds.width;
	}
	public float getHeight(){
		return bounds.height;
	}
	public boolean pressed(float x, float y){
		return bounds.contains(x, y);
	}
	
	public abstract void press();
	
	public void render(SpriteBatch batch){
		batch.draw(image, bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	public void setSize(float width, float height){
		bounds.setSize(width, height);
	}
	
	public void move(float moveX, float moveY){
		this.bounds.x += moveX;
		this.bounds.y += moveY;
	}
	
	public void setPosition(float x, float y){
		bounds.setPosition(x, y);
	}
}
