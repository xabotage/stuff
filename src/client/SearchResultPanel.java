package client;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.net.URL;
import java.util.List;

/**
 * Created by daniel on 3/25/15.
 */
public class SearchResultPanel extends JPanel {
	private SearchController controller;
	private JList imageList;
	private JScrollPane imageArea;
	private ImageIcon icon;
	private List<String> images;

	public SearchResultPanel() {
		setPreferredSize(new Dimension(640, 480));
	}

	public void setSearchResultImages(List<String> imageUrls) {
		removeAll();
		this.images = imageUrls;
		DefaultListModel lm = new DefaultListModel();
		for(String s : images) {
			lm.addElement(s);
		}
		imageList = new JList(lm);
		imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		imageList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//controller.loadImage(images.get(imageList.getSelectedIndex()));
				if (imageArea != null)
					remove(imageArea);
				Image iconImage = null;
				try {
					URL imageUrl = new URL(images.get(imageList.getSelectedIndex()));
					iconImage = ImageIO.read(imageUrl);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				icon = new ImageIcon(iconImage);
				JLabel iconLabel = new JLabel(icon);
				iconLabel.setIcon(icon);
				//iconLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
				imageArea = new JScrollPane(iconLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				imageArea.setPreferredSize(new Dimension(640, 380));
				add(imageArea, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		JScrollPane scrollPane = new JScrollPane(imageList);
		scrollPane.setPreferredSize(new Dimension(640, 80));
		add(scrollPane, BorderLayout.NORTH);
	}
}
