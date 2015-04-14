package client.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import client.state.BatchState;
import client.state.BatchState.BatchStateListener;
import client.state.BatchState.Cell;
import shared.model.Field;

@SuppressWarnings("serial")
public class ImageComponent extends JPanel implements BatchStateListener {
	private BatchState batchState;
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

	public boolean isImageInverted() {
		return imageInverted;
	}

	public void setImageInverted(boolean imageInverted) {
		this.imageInverted = imageInverted;
	}

	public boolean isHighlightsVisible() {
		return highlightsVisible;
	}

	public void setHighlightsVisible(boolean highlightsVisible) {
		this.highlightsVisible = highlightsVisible;
	}

	public ImageComponent(BatchState bState) {
		this.batchState = bState;
		batchState.addListener(this);
		rect = new Rectangle2D.Double(0, 0, 0, 0);
		scale = 1.0;
		translateX = translateY = 0;
		imageInverted = false;
		highlightsVisible = false;
		initDrag();
		listeners = new ArrayList<ImageComponentListener>();

		MouseAdapter mouse = new MouseAdapter() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				scale -= e.getPreciseWheelRotation() * 0.125;
				if(scale <= 0.25)
					scale = 0.25;
				else if(scale >= 4)
					scale = 4;
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
				int w_X = (int) w_Pt.getX();
				int w_Y = (int) w_Pt.getY();

				dragging = true;
				dragStartX = w_X;
				dragStartY = w_Y;
				dragStartTranslateX = translateX;
				dragStartTranslateY = translateY;
				dragTransform = transform;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(batchImage != null) {
					double x = e.getX();
					double y = e.getY();
					x -= getWidth() / 2.0;
					y -= getHeight() / 2.0;
					x /= scale;
					y /= scale;
					x += batchImage.getWidth(null) / 2.0 - translateX;
					y += batchImage.getHeight(null) / 2.0 - translateY;

					y -= batchState.getProject().getFirstYCoord();
					y /= (double)batchState.getProject().getRecordHeight();
					if(y > 0 && y < batchState.getProject().getRecordsPerImage()) {
						int fieldIdx = 0;
						boolean xfound = false;
						for(Field f : batchState.getProject().getFields()) {
							if(fieldIdx == 0) {
								x -= f.getxCoord();
								if (x < 0)
									break;
							}
							if(x - f.getWidth() < 0) {
								xfound = true;
								break;
							}
							x -= f.getWidth();
							fieldIdx++;
						}
						if(xfound) {
							Cell c = batchState.new Cell();
							c.record = (int)Math.floor(y);
							c.field = fieldIdx;
							batchState.setSelectedCell(c);
						}
					}
				}
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
					int w_X = (int) w_Pt.getX();
					int w_Y = (int) w_Pt.getY();

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
		};

		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.addMouseWheelListener(mouse);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(batchImage != null) {
			Graphics2D g2 = (Graphics2D)g;

			/*
			g2.translate(translateX, translateY);
			g2.scale(scale, scale);
			*/
			g2.translate(this.getWidth() / 2.0, this.getHeight() / 2.0);
			g2.scale(scale, scale);
			g2.translate(-batchImage.getWidth(null)/2.0 + translateX, 
					-batchImage.getHeight(null)/2.0 + translateY);
			Rectangle2D bounds = rect.getBounds2D();
			/*
			Image finalImage = new BufferedImage(batchImage);
			if(imageInverted) {
				RescaleOp inverter = new RescaleOp(-1f, 255f, null);
				batchImage = op.filter((BufferedImage)batchImage, null);
			}
			*/
			g2.drawImage(batchImage, (int) bounds.getMinX(), (int) bounds.getMinY(), (int) bounds.getMaxX(), (int) bounds.getMaxY(),
					0, 0, batchImage.getWidth(null), batchImage.getHeight(null), null);
			
			if(highlightsVisible && batchState.getSelectedCell() != null) {
				Cell c = batchState.getSelectedCell();
				int x = batchState.getProject().getFields().get(c.field).getxCoord();
				int y = batchState.getProject().getFirstYCoord() + (batchState.getProject().getRecordHeight() * c.record);
				int width = batchState.getProject().getFields().get(c.field).getWidth();
				int height = batchState.getProject().getRecordHeight();
				g2.setColor(new Color(0, 0, 255, 64));
				g2.fillRect((int) bounds.getMinX() + x, (int) bounds.getMinY() + y, width, height);
			}
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

	@Override
	public void valueChanged(Cell cell, String newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectedCellChanged(Cell newSelectedCell) {
		// TODO Auto-generated method stub
	}

	@Override
	public void batchLoaded() {
		try {
			URL imageUrl = new URL(batchState.getUrlBase() + batchState.getBatchImageUrl());
			batchImage = ImageIO.read(imageUrl);
			rect = new Rectangle2D.Double(0, 0, batchImage.getWidth(null), batchImage.getHeight(null));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
