package client;

import java.awt.*;

import javax.swing.JOptionPane;


public class ClientMain {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {		
			public void run() {
				JTextField userName = new JTextField();
				JPasswordField password = new JPasswordField();
				final JComponent[] inputs = new JComponent[] {
						new JLabel("User"),
						firstName,
						new JLabel("Password"),
						password
				};
				JOptionPane.showMessageDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
				System.out.println("You entered " +
						firstName.getText() + ", " +
						lastName.getText() + ", " +
						password.getText());

				IndexerFrame frame = new IndexerFrame();
				JOptionPane.showOptionDialog(
				//frame.setController(new SearchController(frame));
				frame.setVisible(true);
			}
		});
	}

}
