package imagegallery;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.mybackend.AbstractScreen;

public class ImageGallery extends AbstractScreen {
	public int mode; // mode 0 = gallery, mode 1 = zoom
	public ImageButton selectedImage;
	public float height, width;
	private Reel reel;
	private NavigationButton left, right;

	public boolean focusing() {
		return mode == 1;
	}

	public void focus(ImageButton image) {
		this.selectedImage = image;
		mode = 1;
	}
	
	public float getSectionWidth(){
		return reel.sectionWid;
	}
	
	public float getSectionHeight(){
		return reel.sectionHeight;
	}

	public void zoomOut() {
		mode = 0;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderObjects(SpriteBatch batch) {

	}

	@Override
	public void renderGui(SpriteBatch batch) {
		if (mode == 1) {
			selectedImage.renderZoomed(batch);
		} else {
			reel.render(batch);
			left.render(batch);
			right.render(batch);
		}
	}

	@Override
	public void abstractScreenInit() {
		mode = 0;
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();

		this.setBackgroundImage(null);
		reel = new Reel(this);

		reel.addToReel("doug.jpg");
		reel.addToReel("funny-looking-cats-and-dogs.jpg");
		reel.addToReel("funny.jpeg");
		reel.addToReel("guilty.jpg");
		reel.addToReel("panda.jpg");
		reel.addToReel("images.jpg");
		reel.addToReel("badlogic.jpg");
		reel.addToReel("laptop.jpg");
		reel.addToReel("backoff.jpg");
		reel.addToReel("pup.jpg");

		reel.positionButtons();

		float arrowsize = width/10f;
		left = new NavigationButton(this, "arrowright.png", true);
		
		left.bounds.set(0, 0, arrowsize, arrowsize);

		right = new NavigationButton(this, "arrowleft.png", false);
		right.bounds.set(width - arrowsize, 0f, arrowsize, arrowsize);

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean handleTouchInput(int screenx, int screeny, int pointer,
			int button) {
		System.out.println(screenx + ", " + screeny);

		return false;
	}

	@Override
	public boolean handleKeyInput(int keycode) {
		return false;
	}

	@Override
	public boolean handleKeyUp(int keycode) {
		if (!focusing())
			reel.handleKeyUp(keycode);
		return false;
	}

	@Override
	public boolean handleTouchInputUp(int screenx, int screeny, int pointer,
			int button) {
		if (!focusing()) {
			screeny = (int) (height - screeny);
			reel.handleTouchInput(screenx, screeny);
			System.out.println(screenx + ", " + screeny);

			if (right.pressed(screenx, screeny))
				right.press();
			
			if (left.pressed(screenx, screeny))
				left.press();
		} else
			zoomOut();

		return false;
	}

	@Override
	public boolean handleDrag(int screeny, int screenx, int pointer) {
		return false;
	}

	public void reelLeft() {
		reel.reelLeft();
	}

	public void reelRight() {
		reel.reelRight();
	}

}
