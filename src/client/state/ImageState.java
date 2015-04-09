package client.state;

public class ImageState {
	private int translateX;
	private int translateY;
	private double scale;

	public ImageState() {
		translateX = 0;
		translateY = 0;
		scale = 1.0;
	}

	public int getTranslateX() {
		return translateX;
	}

	public int getTranslateY() {
		return translateY;
	}

	public void setTranslation(int translateX, int translateY) {
		this.translateX = translateX;
		this.translateY = translateY;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}
}
