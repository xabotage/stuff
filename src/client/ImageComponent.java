package client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class ImageComponent extends JPanel {
	private Image batchImage;
	private Rectangle2D rect;
	
	public ImageComponent() {
		rect = new Rectangle2D.Double(0, 0, batchImage.getWidth(null), batchImage.getHeight(null)).getBounds2D();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(batchImage != null) {
			Graphics2D g2 = (Graphics2D)g;
			Rectangle2D bounds = rect.getBounds2D();
			g2.drawImage(batchImage, (int)bounds.getMinX(), (int)bounds.getMinY(), (int)bounds.getMaxX(), (int)bounds.getMaxY(),
					0, 0, batchImage.getWidth(null), batchImage.getHeight(null), null);
		}
	}

}
