package client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageComponent extends JPanel {
	private Image batchImage;
	private Rectangle2D rect;

	private double scale;
	private int translateX;
	private int translateY;
	
	private boolean imageInverted;
	private boolean highlightsVisible;
	
	private boolean dragging;
	private int dragStartX;
	private int dragStartY;
	private int dragStartTranslateX;
	private int dragStartTranslateY;
	AffineTransform dragTransform;
	
	private List<ImageComponentListener> listeners;
	
	public Image getBatchImage() {
		return batchImage;
	}

	public void setBatchImage(Image batchImage) {
		this.batchImage = batchImage;
		this.rect = new Rectangle2D.Double(0, 0, batchImage.getWidth(null), batchImage.getHeight(null)).getBounds2D();
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}


	/**
	 * @return the imageInverted
	 */
	public boolean isImageInverted() {
		return imageInverted;
	}

	/**
	 * @param imageInverted the imageInverted to set
	 */
	public void setImageInverted(boolean imageInverted) {
		this.imageInverted = imageInverted;
	}

	/**
	 * @return the highlightsVisible
	 */
	public boolean isHighlightsVisible() {
		return highlightsVisible;
	}

	/**
	 * @param highlightsVisible the highlightsVisible to set
	 */
	public void setHighlightsVisible(boolean highlightsVisible) {
		this.highlightsVisible = highlightsVisible;
	}

	public ImageComponent() {
		rect = new Rectangle2D.Double(0, 0, 0, 0);
		scale = 1.0;
		translateX = translateY = 0;
		imageInverted = false;
		highlightsVisible = false;
		initDrag();
		listeners = new ArrayList<ImageComponentListener>();

		this.addMouseListener(new MouseAdapter() {
			@Override 
			public void mouseWheelMoved(MouseWheelEvent e) {
				scale += e.getPreciseWheelRotation();
				notifyScaleChanged(scale);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				int d_X = e.getX();
				int d_Y = e.getY();
				
				AffineTransform transform = new AffineTransform();
				transform.scale(scale, scale);
				transform.translate(translateX, translateY);
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try {
					transform.inverseTransform(d_Pt, w_Pt);
				} catch (NoninvertibleTransformException ex) {
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				dragging = true;		
				dragStartX = w_X;
				dragStartY = w_Y;		
				dragStartTranslateX = translateX;
				dragStartTranslateY = translateY;
				dragTransform = transform;
			}

			@Override
			public void mouseDragged(MouseEvent e) {		
				if (dragging) {
					int d_X = e.getX();
					int d_Y = e.getY();
					
					Point2D d_Pt = new Point2D.Double(d_X, d_Y);
					Point2D w_Pt = new Point2D.Double();
					try {
						dragTransform.inverseTransform(d_Pt, w_Pt);
					} catch (NoninvertibleTransformException ex) {
						return;
					}
					int w_X = (int)w_Pt.getX();
					int w_Y = (int)w_Pt.getY();
					
					int w_deltaX = w_X - dragStartX;
					int w_deltaY = w_Y - dragStartY;
					
					translateX = dragStartTranslateX + w_deltaX;
					translateY = dragStartTranslateY + w_deltaY;
					
					notifyTranslationChanged(translateX, translateY);
					
					repaint();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				initDrag();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(batchImage != null) {
			Graphics2D g2 = (Graphics2D)g;
			g2.translate(this.getWidth()/2.0, this.getHeight()/2.0);
			g2.scale(scale, scale);
			g2.translate(-batchImage.getWidth(null)/2.0 + translateX, 
					-batchImage.getHeight(null)/2.0 + translateY);
			Rectangle2D bounds = rect.getBounds2D();
			g2.drawImage(batchImage, (int)bounds.getMinX(), (int)bounds.getMinY(), (int)bounds.getMaxX(), (int)bounds.getMaxY(),
					0, 0, batchImage.getWidth(null), batchImage.getHeight(null), null);
		}
	}
	
	public void setTranslation(int newTranslateX, int newTranslateY) {
		translateX = newTranslateX;
		translateY = newTranslateY;
		this.repaint();
	}
	
	public void addImageComponentListener(ImageComponentListener listener) {
		listeners.add(listener);
	}
	
	private void notifyTranslationChanged(int newTranslateX, int newTranslateY) {
		for (ImageComponentListener l : listeners) {
			l.translationChanged(newTranslateX, newTranslateY);
		}
	}

	private void notifyScaleChanged(double newScale) {
		for (ImageComponentListener l : listeners) {
			l.scaleChanged(newScale);
		}
	}

	private void initDrag() {
		dragging = false;
		dragStartX = 0;
		dragStartY = 0;
		dragStartTranslateX = 0;
		dragStartTranslateY = 0;
		dragTransform = null;
	}

	public interface ImageComponentListener {
		void translationChanged(int newTranslateX, int newTranslateY);
		void scaleChanged(double newScale);
	}
}
