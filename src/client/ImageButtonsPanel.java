package client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ImageButtonsPanel extends JPanel {
	
	private JButton zoomInButton;
	private JButton zoomOutButton;
	private JButton invertButton;
	private JButton toggleHighlightButton;
	private JButton saveButton;
	private JButton submitButton;

	private List<ImageButtonListener> listeners;
	
	public ImageButtonsPanel() {
		this.setLayout(new FlowLayout());
		//this.setPreferredSize(new Dimension(25, 300));

		zoomInButton = new JButton("Zoom In");
		zoomInButton.addActionListener(actionListener);
		add(zoomInButton);

		zoomOutButton = new JButton("Zoom Out");
		zoomOutButton.addActionListener(actionListener);
		add(zoomOutButton);

		invertButton = new JButton("Invert Image");
		invertButton.addActionListener(actionListener);
		add(invertButton);

		toggleHighlightButton = new JButton("Toggle Highlights");
		toggleHighlightButton.addActionListener(actionListener);
		add(toggleHighlightButton);

		saveButton = new JButton("Save");
		saveButton.addActionListener(actionListener);
		add(saveButton);

		submitButton = new JButton("Submit");
		submitButton.addActionListener(actionListener);
		add(submitButton);

	}
	
	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == zoomInButton) {
				for(ImageButtonListener l : listeners) { l.zoomIn(); }
			} else if(e.getSource() == zoomOutButton) {
				for(ImageButtonListener l : listeners) { l.zoomOut(); }
			} else if(e.getSource() == invertButton) {
				for(ImageButtonListener l : listeners) { l.invertImage(); }
			} else if(e.getSource() == toggleHighlightButton) {
				for(ImageButtonListener l : listeners) { l.toggleHighlight(); }
			} else if(e.getSource() == saveButton) {
				for(ImageButtonListener l : listeners) { l.save(); }
			} else if(e.getSource() == submitButton) {
				for(ImageButtonListener l : listeners) { l.submit(); }
			}
		}
	};

	public interface ImageButtonListener {
		public void zoomIn();
		public void zoomOut();
		public void invertImage();
		public void toggleHighlight();
		public void save();
		public void submit();
	}
}

