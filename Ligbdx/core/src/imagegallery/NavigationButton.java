package imagegallery;

public class NavigationButton extends Button{
	boolean left;
	public NavigationButton(ImageGallery parent, int x, int y, int width,
			int height, String path, boolean left) {
		super(parent, x, y, width, height, path);
		this.left = left;
	}
	public NavigationButton(ImageGallery parent, String path, boolean left){
		super(parent, path);
		this.left = left;
	}

	@Override
	public void press() {
		System.out.println("pressed");
		
		if(left){
			gallery.reelLeft();
		}else
			gallery.reelRight();
	}

}
