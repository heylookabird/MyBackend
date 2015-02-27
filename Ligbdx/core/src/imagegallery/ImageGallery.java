package imagegallery;

/*
 * File: ImageGallery.java
 * By: Harjit Randhawa
 * Date: 2/17/15
 * 
 * Compile:
 * 			Desktop: go to Test-desktop and run DesktopLauncher
 * 			Android: go to Test-android and run AndroidLauncher and extract .apk file from
 * 					Test-android/bin
 * 
 * System: Linux, Windows, Android devices, Macintosh
 * 
 * Description: Singleton type class in which all classes need are stored onto
 * Extends from com.backend.Application so that Libgdx backend knows how to compile and run
 * so that program works on multiple platforms
 * 
 * Handles focusing on specific Images in the reel and which inputs should be read based on the mode
 * Also loads all of the images into the Reel
 */

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.test.mybackend.AbstractScreen;

public class ImageGallery extends AbstractScreen {
	public int mode; // mode 0 = gallery, mode 1 = zoom
	public int selectedImage;
	public float height, width;
	private Reel reel;
	private NavigationButton left, right, leftskip, rightskip, zoomout;
	private FileHandle dataFolder;
	private Array<NavigationButton> nav;
	private float zoomOutSize;
	private boolean moving = false;

	public boolean focusing() {
		return mode == 1;
	}

	public void focus(ImageButton image) {
		this.selectedImage = reel.getIndex(image);
		mode = 1;
	}

	public float getSectionWidth() {
		return reel.sectionWid;
	}

	public float getSectionHeight() {
		return reel.sectionHeight;
	}

	public void zoomOut() {
		mode = 0;
	}

	@Override
	public void update(float deltaTime) {
		reel.update();
	}

	@Override
	public void renderObjects(SpriteBatch batch) {

	}

	@Override
	public void renderGui(SpriteBatch batch) {
		if (mode == 1) {
			reel.get(selectedImage).renderZoomed(batch);
			zoomout.render(batch);
		} else {
			reel.render(batch);
		}

		for (NavigationButton button : nav) {
			button.render(batch);
		}
	}

	@Override
	public void abstractScreenInit() {
		mode = 0;
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		nav = new Array<NavigationButton>();

		if (Gdx.app.getType() == ApplicationType.Android) {
			dataFolder = Gdx.files.internal("images/");
		} else if (Gdx.app.getType() == ApplicationType.Desktop) {
			dataFolder = Gdx.files.internal("./bin/images/");
		}

		this.setBackgroundImage(null);
		reel = new Reel(this);

		for (FileHandle fileEntry : dataFolder.list()) {
			String f = "images/" + fileEntry.name();
			System.out.println(f);
			if (f.contains(".jpg") || f.contains(".png")) {
				reel.addToReel(f);
			}

		}

		zoomOutSize = width / 4f;

		reel.positionButtons();
		
		zoomout = new NavigationButton(this, "zoom_out.png", false, false){
			@Override
			public void press(){
				zoomOut();
			}
		};

		float arrowsize = width / 10f;
		zoomout.bounds.set(width/2f - arrowsize/2f, 0, arrowsize, arrowsize);

		left = new NavigationButton(this, "arrowright.png", false, false);

		left.bounds.set(arrowsize, 0, arrowsize, arrowsize);

		right = new NavigationButton(this, "arrowleft.png", true, false);
		right.bounds.set(width - (arrowsize * 2), 0f, arrowsize, arrowsize);

		leftskip = new NavigationButton(this, "skipleft.jpg", false, true);
		leftskip.bounds.set(0, 0, arrowsize, arrowsize);

		rightskip = new NavigationButton(this, "skipright.jpg", true, true);
		rightskip.bounds.set(width - arrowsize, 0, arrowsize, arrowsize);

		nav.addAll(right, left, rightskip, leftskip);

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public boolean handleTouchInput(int screenx, int screeny, int pointer,
			int button) {

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
		screeny = (int) (height - screeny);

		if (!focusing() && !reel.moving()) {
			reel.handleTouchInput(screenx, screeny);

		} else {
			if(zoomout.pressed(screenx, screeny))
				zoomout.press();
		}

		if (!reel.moving()) {
			for (int i = 0; i < nav.size; i++) {
				NavigationButton navi = nav.get(i);
				if (navi.pressed(screenx, screeny))
					navi.press();
			}
		}

		return false;
	}

	@Override
	public boolean handleDrag(int screeny, int screenx, int pointer) {
		return false;
	}

	public void reelLeft(boolean b) {
		reel.reelNext(true, b);
	}

	public void reelRight(boolean b) {
		reel.reelNext(false, b);
	}

}
