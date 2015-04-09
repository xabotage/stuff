package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.*;

import client.ImageButtonsPanel.ImageButtonListener;
import client.state.*;
import shared.model.User;

@SuppressWarnings("serial")
public class IndexerFrame extends JFrame implements ImageButtonListener, ImageComponent.ImageComponentListener {
	public static final int DEFAULT_WIDTH = 640;
	public static final int DEFAULT_HEIGHT = 480;
	private static final int OK_OPTION = 1;

	//private JFrame loginPanel;
	private JMenuBar menuBar;
	private JMenuItem downloadBatch;
	private ImageButtonsPanel imageButtons;
	private ImageComponent imageComponent;
	private ImageNavigator imageNavigator;

	private IndexerController controller;

	private WindowState windowState;
	private ImageState imageState;
	private BatchState batchState;

	public IndexerFrame() {
		super();
		this.setSize(IndexerFrame.DEFAULT_WIDTH, IndexerFrame.DEFAULT_HEIGHT);
		this.setTitle("Search Gui");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(200, 200);
		this.setLayout(new BorderLayout());

		setupMenu();
		imageButtons = new ImageButtonsPanel();
		add(imageButtons, BorderLayout.NORTH);

		imageComponent = new ImageComponent();
		imageComponent.addImageComponentListener(this);

		imageNavigator = new ImageNavigator();

		JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JSplitPane bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JTabbedPane leftTabbedPane = new JTabbedPane();
		JTabbedPane rightTabbedPane = new JTabbedPane();

		rightTabbedPane.add("Image Navigator", imageNavigator);

		bottomSplitPane.add(leftTabbedPane, JSplitPane.LEFT);
		bottomSplitPane.add(rightTabbedPane, JSplitPane.RIGHT);
		mainSplitPane.add(bottomSplitPane, JSplitPane.BOTTOM);

		mainSplitPane.add(imageComponent, JSplitPane.TOP);
		add(mainSplitPane, BorderLayout.CENTER);

		this.setVisible(false);

		pack();
	}

	public void showLoginDialog() {
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
		final Object[] inputs = new Object[] { loginComp, "OK", "Exit" };
		//JOptionPane.showMessageDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
		int option = JOptionPane.showOptionDialog(this, null, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, inputs, null);

		if(option == JOptionPane.CLOSED_OPTION) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		
		if(option == OK_OPTION) {
			User user =  controller.attemptLogin(userName.getText(), new String(password.getPassword()));
			if(user != null) {
				loginUser(user);
			}
			else {
				JOptionPane.showMessageDialog(this, "Invalid User Credentials");
				showLoginDialog();
			}
		}
	}

	private void loginUser(User user) {
		String welcome = "Welcome, " + user.getFirstName() + " " + user.getLastName() + ". You have " + user.getIndexedRecords() + "indexed records.";
		JOptionPane.showMessageDialog(this, welcome);
		controller.loadUserProperties(user);
		this.setVisible(true);
		repaint();
		revalidate();
		pack();
	}

	private void setupMenu() {
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

		menuBar.add(menu);
		menuBar.setVisible(true);
		this.setJMenuBar(menuBar);
	}



	/** --------------      Image Buttons Listener Functions **/

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

	/** --------------      Image Component Listener Functions **/

	public void translationChanged(int newTranslateX, int newTranslateY) {
		imageComponent.setTranslation(newTranslateX, newTranslateY);
		imageNavigator.setTranslation(newTranslateX, newTranslateY);
		imageState.setTranslation(newTranslateX, newTranslateY);
	}

	public void scaleChanged(double newScale) {
		imageComponent.setScale(newScale);
		imageNavigator.setScale(newScale);
		imageState.setScale(newScale);
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
