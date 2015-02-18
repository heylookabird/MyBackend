package imagegallery;



import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.test.mybackend.AbstractScreen;

public class ImageGallery extends AbstractScreen {
	public int mode; // mode 0 = gallery, mode 1 = zoom
	public ImageButton selectedImage;
	public float height, width;
	private Reel reel;
	private NavigationButton left, right, leftskip, rightskip;
	private FileHandle dataFolder;
	private Array<NavigationButton> nav;

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
			
			for(NavigationButton button: nav){
				button.render(batch);
			}
		}
	}

	@Override
	public void abstractScreenInit() {
		mode = 0;
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		nav = new Array<NavigationButton>();
		
		if(Gdx.app.getType() == ApplicationType.Android){
			dataFolder = Gdx.files.internal("images/");
		}else if(Gdx.app.getType() == ApplicationType.Desktop){
			dataFolder = Gdx.files.internal("./bin/images/");
		}
		

		this.setBackgroundImage(null);
		reel = new Reel(this);
		
		for (FileHandle fileEntry : dataFolder.list()) {
			String f = "images/" + fileEntry.name();
			System.out.println(f);
			if (f.contains(".jpg")
					|| f.contains(".png")) {
				reel.addToReel(f);
			}

		}

		
		reel.positionButtons();

		float arrowsize = width/10f;
		left = new NavigationButton(this, "arrowright.png", true, false);
		
		left.bounds.set(arrowsize *2, 0, arrowsize, arrowsize);

		right = new NavigationButton(this, "arrowleft.png", false, false);
		right.bounds.set(width - (arrowsize *3), 0f, arrowsize, arrowsize);
		
		leftskip = new NavigationButton(this, "skipleft.jpg", true, true);
		leftskip.bounds.set(0, 0, arrowsize, arrowsize);
		
		rightskip = new NavigationButton(this, "skipright.jpg", false, true);
		rightskip.bounds.set(width - arrowsize, 0 , arrowsize, arrowsize);
		
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
		if (!focusing()) {
			screeny = (int) (height - screeny);
			reel.handleTouchInput(screenx, screeny);
			
		for(int i = 0; i < nav.size; i++){
			NavigationButton navi = nav.get(i);
			if(navi.pressed(screenx, screeny))
				navi.press();
		}
		} else
			zoomOut();

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
