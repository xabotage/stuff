package client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.util.List;

import client.IndexerController;
import client.IndexerProperties;
import client.gui.ImageButtonsPanel.ImageButtonListener;
import client.gui.table.TableEntryPanel;
import client.state.*;
import shared.model.*;

@SuppressWarnings({"serial", "rawtypes"})
public class IndexerFrame extends JFrame implements ImageButtonListener,
		ImageComponent.ImageComponentListener,
		BatchState.BatchStateListener {
	public static final int DEFAULT_WIDTH = 640;
	public static final int DEFAULT_HEIGHT = 480;
	public static final String PROPERTIES_PATH = "userProperties/";


	//private JFrame loginPanel;
	private JMenuBar menuBar;
	private JMenuItem downloadBatch;
	private JMenuItem logoutMenuItem;
	private JMenuItem exitMenuItem;
	private ImageButtonsPanel imageButtons;
	private ImageComponent imageComponent;
	private ImageNavigator imageNavigator;
	private TableEntryPanel tableEntry;
	private FormEntryPanel formEntry;
	private FieldHelpPanel fieldHelpPanel;

	private JComboBox projectChooser; 
	private JSplitPane mainSplitPane;
	private JSplitPane bottomSplitPane;

	private IndexerController controller;

	private WindowState windowState;
	private ImageState imageState;
	private BatchState batchState;
	private User currentUser;

	private IndexerProperties properties;

	public IndexerFrame() {
		super();
		this.setSize(IndexerFrame.DEFAULT_WIDTH, IndexerFrame.DEFAULT_HEIGHT);
		this.setTitle("Record Indexer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(200, 200);
		this.setLayout(new BorderLayout());
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				saveUserProperties();
				System.exit(0);
			}
		});
		
		resetIndexerFrame();
	}
	
	private void resetIndexerFrame() {
		this.setVisible(false);
		currentUser = null;
		imageState = new ImageState();
		batchState = new BatchState();
		batchState.addListener(this);
		windowState = new WindowState();
		properties = new IndexerProperties();

		setupMenu();
		addImageButtons();
		createSplitPaneComponents();
	}

	public void showLoginDialog() {
		JTextField userName = new JTextField(10);
		userName.requestFocus();
		JPasswordField password = new JPasswordField(10);
		JComponent loginComp = new JPanel(new FlowLayout());
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
		//loginComp.add(executeButton);

		int option = JOptionPane.showOptionDialog(this, loginComp, "Login", JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.PLAIN_MESSAGE, null, null, null);

		if(option == JOptionPane.CLOSED_OPTION) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		
		if(option == JOptionPane.OK_OPTION) {
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
		String welcome = "Welcome, " + user.getFirstName() + " " + user.getLastName() + ". \nYou have " + user.getIndexedRecords() + " indexed records.";
		JOptionPane.showMessageDialog(this, welcome);
		this.currentUser = user;
		loadUserProperties();
		this.setVisible(true);
		repaint();
		revalidate();
	}

	private void setupMenu() {
		this.menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");

		this.downloadBatch = new JMenuItem("Download Batch");
		downloadBatch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayDownloadBatchDialog();
			}
		});
		//downloadBatch.setEnabled(false);
		menu.add(downloadBatch);

		logoutMenuItem = new JMenuItem("Logout");
		logoutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});
		menu.add(logoutMenuItem);

		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logoutAndExit();
			}
		});
		menu.add(exitMenuItem);

		menuBar.add(menu);
		menuBar.setVisible(true);
		this.setJMenuBar(menuBar);
	}
	
	private void addImageButtons() {
		if(imageButtons != null)
			this.remove(imageButtons);
		imageButtons = new ImageButtonsPanel();
		imageButtons.addImageButtonListener(this);
		add(imageButtons, BorderLayout.NORTH);
	}
	
	private void createSplitPaneComponents() {
		if(mainSplitPane != null)
			this.remove(mainSplitPane);

		imageComponent = new ImageComponent(batchState);
		imageComponent.addImageComponentListener(this);

		imageNavigator = new ImageNavigator();
		
		tableEntry = new TableEntryPanel(batchState);
		
		formEntry = new FormEntryPanel(batchState);
		
		fieldHelpPanel = new FieldHelpPanel(batchState);

		mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JTabbedPane leftTabbedPane = new JTabbedPane();
		JTabbedPane rightTabbedPane = new JTabbedPane();

		leftTabbedPane.add("Table Entry", tableEntry);
		leftTabbedPane.add("Form Entry", formEntry);
		rightTabbedPane.add("Image Navigator", imageNavigator);
		rightTabbedPane.add("Field Help", fieldHelpPanel);

		bottomSplitPane.add(leftTabbedPane, JSplitPane.LEFT);
		bottomSplitPane.add(rightTabbedPane, JSplitPane.RIGHT);
		mainSplitPane.add(bottomSplitPane, JSplitPane.BOTTOM);

		mainSplitPane.add(imageComponent, JSplitPane.TOP);
		add(mainSplitPane, BorderLayout.CENTER);
	}
	
	@SuppressWarnings("unchecked")
	private void displayDownloadBatchDialog() {
		List<Project> projects = controller.getProjects(currentUser);
		projectChooser = new JComboBox(projects.toArray());
		JButton viewSampleButton = new JButton("View Sample");
		viewSampleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayImageSampleDialog(controller.getSampleImageForProject((Project) projectChooser.getSelectedItem(), currentUser));
			}
		});
		JPanel downloadBatchPanel = new JPanel(new FlowLayout());
		downloadBatchPanel.add(new JLabel("Project:"));
		downloadBatchPanel.add(projectChooser);
		downloadBatchPanel.add(viewSampleButton);
		int result = JOptionPane.showOptionDialog(this, downloadBatchPanel, "Download New Batch", 
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
		if(result == JOptionPane.OK_OPTION) {
			Project p = (Project)projectChooser.getSelectedItem();
			String imageUrl = controller.downloadBatch(p, currentUser).getImageFile();
			initializeBatchAndProject(p, imageUrl, new String[p.getRecordsPerImage()][p.getFields().size()]);
		}
	}

	private void initializeBatchAndProject(Project project, String imageUrl, String[][] values) {
		downloadBatch.setEnabled(false);
		batchState.loadBatch(project, imageUrl, values);
	}
	
	private void displayImageSampleDialog(String sampleUrl) {
		Image iconImage = null;
		try {
			URL imageUrl = new URL(sampleUrl);
			iconImage = ImageIO.read(imageUrl);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(iconImage);
		Image img = icon.getImage();
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		g.drawImage(img, icon.getIconWidth()/4, icon.getIconHeight()/4, icon.getIconWidth()/2, icon.getIconHeight()/2, null);
		icon = new ImageIcon(bi);

		JLabel iconLabel = new JLabel(icon);
		iconLabel.setIcon(icon);
		//iconLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		//imageArea = new JScrollPane(iconLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//imageArea.setPreferredSize(new Dimension(640, 380));
		iconLabel.setPreferredSize(new Dimension(icon.getIconWidth() / 2, icon.getIconHeight() / 2));
		JOptionPane.showMessageDialog(this, iconLabel, "Sample Image", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void logout() {
		saveUserProperties();
		resetIndexerFrame();
		showLoginDialog();
	}

	public void logoutAndExit() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	public void loadUserProperties() {
		try {
			batchState.setUrlBase("http://" + controller.getHost() + ":" + controller.getPort() + "/");
			File propFile = new File(PROPERTIES_PATH + currentUser.getUserName() + ".properties");
			if(!propFile.exists()) {
				loadDefaultProperties();
			} else {
				properties.load(new FileReader(propFile));
				
				// Image properties
				translationChanged(properties.getIntProperty("translationX"), properties.getIntProperty("translationY"));
				scaleChanged(properties.getDoubleProperty("scale"));
				imageComponent.setHighlightsVisible(properties.getBoolProperty("highlightsVisible"));
				imageComponent.setImageInverted(properties.getBoolProperty("imageInverted"));
				
				// Window properties
				this.setSize(properties.getIntProperty("windowSizeX"), properties.getIntProperty("windowSizeY"));
				revalidate();
				repaint();
				this.setLocation(properties.getIntProperty("windowPosX"), properties.getIntProperty("windowPosY"));
				mainSplitPane.setDividerLocation(properties.getIntProperty("mainSplitLocation"));
				bottomSplitPane.setDividerLocation(properties.getIntProperty("bottomSplitLocation"));
				revalidate();
				repaint();

				// Batch properties
				if(currentUser.getCurrentBatch() != -1) { // user has a batch assigned
					String imageUrl = properties.getProperty("batchImageUrl");
					String [][] values = properties.getValuesProperty();
					initializeBatchAndProject(controller.getCurrentUserProjectWithId(properties.getIntProperty("projectId"), currentUser), imageUrl, values);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			this.repaint();
			this.revalidate();
		}
	}
	
	public void saveUserProperties() {
		if(currentUser == null)
			return;

		// Image properties
		properties.setIntProperty("translationX", imageState.getTranslateX());
		properties.setIntProperty("translationY", imageState.getTranslateY());
		properties.setDoubleProperty("scale", imageState.getScale());
		properties.setBoolProperty("highlightsVisible", imageComponent.isHighlightsVisible());
		properties.setBoolProperty("imageInverted", imageComponent.isImageInverted());
		
		// Window properties
		properties.setIntProperty("windowSizeX", this.getWidth());
		properties.setIntProperty("windowSizeY", this.getHeight());
		Point pos = this.getLocationOnScreen();
		properties.setIntProperty("windowPosX", pos.x);
		properties.setIntProperty("windowPosY", pos.y);
		properties.setIntProperty("mainSplitLocation", mainSplitPane.getDividerLocation());
		properties.setIntProperty("bottomSplitLocation", bottomSplitPane.getDividerLocation());
		
		// Batch properties
		properties.setProperty("batchImageUrl", batchState.getBatchImageUrl());
		if(batchState.getProject() != null) {
			properties.setIntProperty("projectId", batchState.getProject().getProjectId());
			properties.setValuesProperty(batchState.getValues());
		}

		try {
			properties.store((Writer)(new PrintWriter(new File(PROPERTIES_PATH + currentUser.getUserName() + ".properties"))), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadDefaultProperties() {
		// Image properties
		translationChanged(0, 0);
		scaleChanged(1.0);
		imageComponent.setHighlightsVisible(false);
		imageComponent.setImageInverted(false);
		
		// Window properties
		this.setSize(640, 480);
		this.setLocation(200, 200);
		mainSplitPane.setDividerLocation(400);
		bottomSplitPane.setDividerLocation(240);
	}


	/** --------------      Image Buttons Listener Functions **/

	public void zoomIn() {
		double curScale = imageState.getScale();
		if(curScale < 4.0) {
			curScale += 0.25;
			scaleChanged(curScale);
		}
	}

	public void zoomOut() {
		double curScale = imageState.getScale();
		if(curScale > 0.25) {
			curScale -= 0.25;
			scaleChanged(curScale);
		}
	}

	public void invertImage() {
		imageComponent.setImageInverted(!imageComponent.isImageInverted());
		repaint();
	}

	public void toggleHighlight() {
		imageComponent.setHighlightsVisible(!imageComponent.isHighlightsVisible());
		repaint();
	}

	public void save() {
		saveUserProperties();
	}

	public void submit() {
		controller.submitBatch(currentUser, batchState.getValues());
	}

	/** --------------      Image Component Listener Functions **/

	public void translationChanged(int newTranslateX, int newTranslateY) {
		imageComponent.setTranslation(newTranslateX, newTranslateY);
		imageNavigator.setTranslation(newTranslateX, newTranslateY);
		imageState.setTranslation(newTranslateX, newTranslateY);
		repaint();
	}

	public void scaleChanged(double newScale) {
		imageComponent.setScale(newScale);
		imageNavigator.setScale(newScale);
		imageState.setScale(newScale);
		repaint();
	}
	
	/** --------------      Batch State Listener Functions **/

	public void valueChanged(BatchState.Cell cell, String newValue) {
	}

	public void selectedCellChanged(BatchState.Cell newSelectedCell) {
		repaint();
	}

	public void batchLoaded() {
	}

	public IndexerController getController() {
		return controller;
	}

	public void setController(IndexerController controller) {
		this.controller = controller;
	}
}
