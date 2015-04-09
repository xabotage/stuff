package client;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class ImageNavigator extends JPanel {
	private double scale;
	private int translateX;
	private int translateY;

	public ImageNavigator() {

	}

	@Override
	protected void paintComponent(Graphics g) {
		//Rectangle2D rect = new Rectangle2D.Double(this.getHeight(),
	}

	public void setTranslation(int newTranslationX, int newTranslationY) {
		this.translateX = newTranslationX;
		this.translateY = newTranslationY;
	}

	public void setScale(double newScale) {
		this.scale = newScale;
	}
}
