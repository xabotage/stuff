package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.*;

import client.ImageButtonsPanel.ImageButtonListener;

import shared.model.*;

@SuppressWarnings("serial")
public class IndexerFrame extends JFrame implements ImageButtonListener {
	public static final int DEFAULT_WIDTH = 640;
	public static final int DEFAULT_HEIGHT = 480;

	private JFrame loginPanel;
	private JMenuBar menuBar;
	private JMenuItem downloadBatch;
	private ImageButtonsPanel imageButtons;
	private IndexerController controller;

	public IndexerFrame() {
		super();
		this.setSize(IndexerFrame.DEFAULT_WIDTH, IndexerFrame.DEFAULT_HEIGHT);
		this.setTitle("Search Gui");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(200, 200);
		this.setLayout(new BorderLayout());

		this.menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");

		this.downloadBatch = new JMenuItem("Download Batch");
		downloadBatch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		downloadBatch.setEnabled(false);
		menu.add(downloadBatch);
		
		JMenuItem logout = new JMenuItem("Logout");
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		menu.add(logout);

		JMenuItem exit = new JMenuItem("Exit");
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		menu.add(exit);

		this.setJMenuBar(menuBar);
		
		imageButtons = new ImageButtonsPanel();
		add(imageButtons, BorderLayout.NORTH);

		this.setVisible(false);
		showLoginDialog();
		
		/*
		settingsPanel = new SettingsPanel();
		add(settingsPanel, BorderLayout.NORTH);

		searchPanel = new SearchPanel();
		add(searchPanel, BorderLayout.CENTER);
		searchPanel.setVisible(false);

		searchResultPanel = new SearchResultPanel();
		add(searchResultPanel, BorderLayout.SOUTH);
		searchResultPanel.setVisible(false);
		*/
		pack();
	}

	private void showLoginDialog() {
		JTextField userName = new JTextField(10);
		JPasswordField password = new JPasswordField(10);
		JComponent loginComp = new JPanel();
		loginComp.add(new JLabel("User"));
		loginComp.add(userName);
		loginComp.add(new JLabel("Password"));
		loginComp.add(password);

		JButton executeButton = new JButton("Log In");
		executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//controller.getProjectsFromServer();
			}
		});

		loginComp.add(executeButton);

		loginComp.setLayout(new FlowLayout());
		final Object[] inputs = new Object[] { loginComp, "OK" };
		//JOptionPane.showMessageDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
		int option = JOptionPane.showOptionDialog(this, null, "Login", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, inputs, null);

		if(option == JOptionPane.CLOSED_OPTION) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		
		if(option == JOptionPane.OK_OPTION) {
			if(controller.attemptLogin(userName.getText(), password.getPassword().toString()))
				JOptionPane.showMessageDialog(this, "fathead");
			else
				JOptionPane.showMessageDialog(this, "fail");
		}
	}
	
	public void zoomIn() {
	}

	public void zoomOut() {
	}

	public void invertImage() {
	}

	public void toggleHighlight() {
	}

	public void save() {
	}

	public void submit() {
	}

	public IndexerController getController() {
		return controller;
	}

	public void setController(IndexerController controller) {
		this.controller = controller;
	}
	

	/*
	public void generateProjectsComponent(List<Project> projects) {
		searchPanel.setVisible(true);
		searchPanel.setProjects(projects);
		pack();
	}
	

	public String getSearchKeywords() {
		return searchPanel.getSearchKeywords();
	}

	public List<Integer> getSelectedFields() {
		return searchPanel.getSelectedFields();
	}

	public void setSearchResultImages(List<String> images) {
		searchResultPanel.setVisible(true);
		searchResultPanel.setSearchResultImages(images);
		pack();
	}
	
	public String getUserName() {
		return settingsPanel.getUserName();
	}

	public String getPassword() {
		return settingsPanel.getPassword();
	}

	public String getHost() {
		return settingsPanel.getHost();
	}

	public String getPort() {
		return settingsPanel.getPort();
	}
	*/

}
