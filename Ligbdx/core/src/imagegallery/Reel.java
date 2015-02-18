package imagegallery;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Reel {
	private Array<ImageButton> images;
	private int size = 0;
	private int reelLength = 6;
	private int leftMostImage = 0, rightMostImage;
	private float reelStart, reelEnd, spacing;

	public float sectionWid, sectionHeight;
	ImageGallery gallery;

	public Reel(ImageGallery parent) {
		images = new Array<ImageButton>();
		this.gallery = parent;

		reelStart = gallery.width / 20;
		reelEnd = gallery.width - reelStart;
		spacing = gallery.width / 40f;

		sectionWid = (gallery.width * .95f) / reelLength;
		sectionHeight = gallery.height * .5f;

	}

	public void positionButtons() {
		for (int j = 0; j < size; j++) {
			float yPos = gallery.height / 2f - images.get(j).bounds.height / 2f;
			images.get(j).resize();
			images.get(j).setPosition(reelStart + (j * (sectionWid + spacing)),
					yPos);

		}
	}

	public void reelLeft() {
		float rightMostLoc = images.get(rightMostImage).bounds.x;
		for (ImageButton button : images) {
			button.move(-(sectionWid + spacing), 0);
		}

		ImageButton leftMost = images.get(leftMostImage);
		leftMost.bounds.x = rightMostLoc;// moved left to location of old
											// rightMost
		rightMostImage = leftMostImage;
		leftMostImage++;
		if (leftMostImage == size)
			leftMostImage = 0;

	}

	public void reelNext(boolean left, boolean skip) {

		if (skip) {
			int i = 1;

			while (i < reelLength) {
				if (left)
					reelLeft();
				else
					reelRight();

				i++;

			}
		} else {
			if (left)
				reelLeft();
			else
				reelRight();
		}
	}

	public void update() {

	}

	public void reelRight() {
		float leftMostLoc = images.get(leftMostImage).bounds.x;
		for (ImageButton button : images) {
			button.move((sectionWid + spacing), 0);
		}

		ImageButton rightMost = images.get(rightMostImage);
		rightMost.bounds.x = leftMostLoc;// moved left to location of old
											// rightMost
		leftMostImage = rightMostImage;
		rightMostImage--;
		if (rightMostImage < 0)
			rightMostImage = size - 1;
	}

	public void addToReel(ImageButton image) {
		images.add(image);
		size++;
		rightMostImage = size - 1;

	}

	public void addToReel(String string) {
		addToReel(new ImageButton(gallery, string));
	}

	public void handleTouchInput(int screenx, int screeny) {
		for (ImageButton button : images) {
			if (button.pressed(screenx, screeny))
				button.press();
		}
	}

	public void render(SpriteBatch batch) {
		for (int i = 0; i < size; i++) {
			ImageButton button = images.get(i);
			if (button.bounds.x < reelEnd && button.bounds.x >= 0)
				button.render(batch);
		}
	}

	public void handleKeyUp(int keycode) {
		if (keycode == Keys.LEFT)
			reelLeft();
		else if (keycode == Keys.RIGHT)
			reelRight();
	}

}
