package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by daniel on 3/25/15.
 */
public class SearchKeywordsPanel extends JPanel {
	private JTextField searchWords;
	private JLabel searchLabel;
	private JButton searchButton;
	private SearchController controller;

	public SearchKeywordsPanel() {
		setLayout(new FlowLayout());
		searchLabel = new JLabel("Search Keywords:");
		add(searchLabel);

		searchWords = new JTextField(30);
		add(searchWords);

		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.searchKeywords();
			}
		});
		add(searchButton);
	}

	public String getSearchKeywords() {
		return searchWords.getText();
	}

	public void setController(SearchController controller) {
		this.controller = controller;
	}
}
