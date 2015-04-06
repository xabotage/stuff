package client.search;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class SettingsPanel extends JPanel {
	
	private JTextField hostTextField;
	private JTextField portTextField;
	private JButton executeButton;
	private JTextField userNameField;
	private JTextField passwordField;
	private SearchController controller;
	
	public SettingsPanel() {
		super();
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(640, 80));
		add(new JLabel("Host:"));
		hostTextField = new JTextField(10);
		hostTextField.setText("localhost");
		add(hostTextField);
		hostTextField.setMinimumSize(hostTextField.getPreferredSize());

		add(new JLabel("Port:"));
		portTextField = new JTextField(4);
		portTextField.setText("8989");
		portTextField.setMinimumSize(portTextField.getPreferredSize());
		add(portTextField);

		add(new JLabel("User:"));
		userNameField = new JTextField(7);
		add(userNameField);

		add(new JLabel("Password:"));
		passwordField = new JTextField(7);
		add(passwordField);

		executeButton = new JButton("Log In");
		add(executeButton);
		/*
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		add(Box.createRigidArea(DOUBLE_HSPACE));
		add(new JLabel("HOST:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		hostTextField = new JTextField(30);
		hostTextField.setMinimumSize(hostTextField.getPreferredSize());
		add(hostTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(new JLabel("PORT:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		portTextField = new JTextField(10);
		portTextField.setMinimumSize(portTextField.getPreferredSize());
		add(portTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(Box.createRigidArea(TRIPLE_HSPACE));

		executeButton = new JButton("Log In");
		add(executeButton);
		add(Box.createRigidArea(DOUBLE_HSPACE));
		*/
		
		setMaximumSize(getPreferredSize());
		
		executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.getProjectsFromServer();
			}
		});
	}
	
	public void setHost(String value) {
		hostTextField.setText(value);
	}

	public String getHost() {
		return hostTextField.getText();
	}

	public void setPort(String value) {
		portTextField.setText(value);
	}

	public String getPort() {
		return portTextField.getText();
	}
	
	public String getUserName() {
		return userNameField.getText();
	}

	public String getPassword() {
		return passwordField.getText();
	}

	public SearchController getController() {
		return controller;
	}

	public void setController(SearchController controller) {
		this.controller = controller;
	}
}

