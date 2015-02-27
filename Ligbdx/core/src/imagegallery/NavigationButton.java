package imagegallery;

public class NavigationButton extends Button {
	boolean left;
	boolean skip;

	public NavigationButton(ImageGallery parent, int x, int y, int width,
			int height, String path, boolean left, boolean skip) {
		super(parent, x, y, width, height, path);
		this.left = left;
		this.skip = skip;
	}

	public NavigationButton(ImageGallery parent, String path, boolean left,
			boolean skip) {
		super(parent, path);
		this.left = left;
		this.skip = skip;
	}

	@Override
	public void press() {
		if (!gallery.focusing()) {
			if (left) {
				gallery.reelLeft(skip);
			} else
				gallery.reelRight(skip);
		} else {
			if (left) {
				gallery.selectedImage--;
				gallery.reelLeft(false);
			} else
				gallery.selectedImage++;
				gallery.reelRight(false);
		}
	}

}
