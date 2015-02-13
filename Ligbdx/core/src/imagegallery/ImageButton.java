package imagegallery;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImageButton extends Button{

	float zoomx, zoomy, zoomdimx, zoomdimy;
	public ImageButton(ImageGallery parent, int x, int y, int width,
			int height, String path) {
		super(parent, x, y, width, height, path);
		init();
	}

	public ImageButton(ImageGallery parent, String path){
		super(parent, path);
		init();
	}
	
	private void init() {
		initZoom();		
	}
	
	private void initZoom(){
		float w = image.getWidth();
		float h = image.getHeight();
		
		//shrinks if too big
		while (w > gallery.width || h > gallery.height){
			w = w * .85f;
			h = h * .85f;
		}
		
		//expands if it can
		while(w * 1.15f < gallery.width && h * 1.15f < gallery.height){
			w = w*1.15f;
			h = h*1.15f;
		}
		
		
		zoomdimx = w;
		zoomdimy = h;
			
		zoomx = gallery.width/2f - zoomdimx/2f;
		zoomy = gallery.height/2f - zoomdimy/2f;
	}

	@Override
	public void press() {
		gallery.focus(this);
	}

	public void renderZoomed(SpriteBatch batch) {
		batch.draw(image, zoomx, zoomy, zoomdimx, zoomdimy);
	}

}
