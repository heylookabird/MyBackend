package imagegallery;

/*File: Reel.java
 * By: Harjit Randhawa
 * Date last revision: 2/17/15
 * 
 * Description: Holds all images and places them based on device width and height
 * All images are virtually there but are only rendered or checked if visible in screen
 * 
 */

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Reel {
	private Array<ImageButton> images;
	private int size = 0;
	private int reelLength = 6;
	private int leftMostImage = 0, rightMostImage;
	private float reelStart, reelEnd, spacing, reelSpeed, frames, currentFrame, rightEndPositionX;
	private boolean left;
	public float sectionWid, sectionHeight;
	ImageGallery gallery;

	public Reel(ImageGallery parent) {
		images = new Array<ImageButton>();
		this.gallery = parent; // to provide access to screen specifications

		reelStart = gallery.width / 20;
		reelEnd = gallery.width - reelStart;
		spacing = gallery.width / 40f;

		sectionWid = (gallery.width * .95f) / reelLength;
		sectionHeight = gallery.height * .5f;
		
		frames = 20;
		
		reelSpeed = (sectionWid + spacing)/frames;
		currentFrame = 0;
		

	}
	
	public ImageButton get(int index){		
		if(index >= images.size){
			index = index - images.size;
		}else if(index < 0){
			index = index + images.size;
		}
		
		index = Math.abs(index%images.size);
		
		return images.get(index);
	}
	

	// positions buttons through iteration and determines their y position by
	// finding the middle of the screen
	// and subtracting half the height of each image therefore placing the
	// middle of the image at the midpoint of the screen
	public void positionButtons() {
		for (int j = 0; j < size; j++) {
			float yPos = gallery.height / 2f - images.get(j).bounds.height / 2f;
			images.get(j).resize();
			images.get(j).setPosition(reelStart + (j * (sectionWid + spacing)),
					yPos);
		}
		
		rightEndPositionX = images.get(rightMostImage).bounds.x;

	}
	
	private void repositionEnds(boolean left){
		if (left){
			ImageButton leftMost = images.get(leftMostImage);
			leftMost.bounds.x = rightEndPositionX;// moved left to location of old
												// rightMost
			rightMostImage = leftMostImage;
			leftMostImage++;
			if (leftMostImage == size)
				leftMostImage = 0;
			
		}else{
			ImageButton rightMost = images.get(rightMostImage);
			rightMost.bounds.x = reelStart;// moved left to location of old
												// rightMost
			leftMostImage = rightMostImage;
			rightMostImage--;
			if (rightMostImage < 0)
				rightMostImage = size - 1;
		}
		
	}

	// stores position of the right most image so that the current left most
	// image can
	// be repositioned there and then shifts all the images over left.
	// After the shift, the left most image is moved to the position saved
	// before shifting images over to the left
	private void reelLeft() {
		for (ImageButton button : images) {
			button.move(-reelSpeed, 0);
		}
	}

	// public method that passes in whether it is left and whether
	// to shift so that it shifts enough images over so that it shows
	// a new set of images. Simply calls reelLeft or reelRight a certain
	// number of times depending on booleans
	public void reelNext(boolean left, boolean skip) {

		if (skip) {
			currentFrame = frames * reelLength;
		} else {
			currentFrame = frames;
		}
		this.left = left;
	}

	// for handling transitions and animations
	// will be called every frame
	public void update() {
		currentFrame--;
		
		if(currentFrame >= 0){
			move(left);
			
			if(currentFrame%frames == 0){
				this.repositionEnds(left);
			}
		}
	}
	
	private void move(boolean left){
		if(left){
			reelLeft();
		}else{
			reelRight();
		}
	}

	// same as reelLeft
	private void reelRight() {
		for (ImageButton button : images) {
			button.move(reelSpeed, 0);
		}
	}

	// adds an image to the reel
	public void addToReel(ImageButton image) {
		images.add(image);
		size++;
		rightMostImage = size - 1;
		

	}

	// allows for easier adding to reel by just passing a string
	// and constructing the image button before calling addToReel(ImageButton)
	public void addToReel(String string) {
		addToReel(new ImageButton(gallery, string));
	}

	public void handleTouchInput(int screenx, int screeny) {
		for (ImageButton button : images) {
			if (button.bounds.x < reelEnd && button.bounds.x >= 0){
				if (button.pressed(screenx, screeny))
					button.press();
			}
		}
	}
	
	public int getIndex(ImageButton button){
		return images.indexOf(button, true);
	}

	// only renders within screen
	public void render(SpriteBatch batch) {
		for (int i = 0; i < size; i++) {
			ImageButton button = images.get(i);
			if (button.bounds.x < reelEnd && button.bounds.x >= 0)
				button.render(batch);
		}
	}

	//computer debugging
	public void handleKeyUp(int keycode) {
		if (keycode == Keys.LEFT)
			reelLeft();
		else if (keycode == Keys.RIGHT)
			reelRight();
	}

	public boolean moving() {
		return currentFrame >=0;
	}

}
