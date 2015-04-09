package client.state;

public class WindowState {
	private int windowPositionX;
	private int windowPositionY;
	private int windowWidth;
	private int windowHeight;
	private int horizontalSplitPosition;
	private int verticalSplitPosition;

	public int getWindowPositionX() {
		return windowPositionX;
	}

	public void setWindowPositionX(int windowPositionX) {
		this.windowPositionX = windowPositionX;
	}

	public int getWindowPositionY() {
		return windowPositionY;
	}

	public void setWindowPositionY(int windowPositionY) {
		this.windowPositionY = windowPositionY;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

	public int getHorizontalSplitPosition() {
		return horizontalSplitPosition;
	}

	public void setHorizontalSplitPosition(int horizontalSplitPosition) {
		this.horizontalSplitPosition = horizontalSplitPosition;
	}

	public int getVerticalSplitPosition() {
		return verticalSplitPosition;
	}

	public void setVerticalSplitPosition(int verticalSplitPosition) {
		this.verticalSplitPosition = verticalSplitPosition;
	}

}
