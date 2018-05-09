package controller;

import javax.swing.*;
import java.awt.*;

public class UserViewPage {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void launchUserViewPage(
	) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserViewPage window = new UserViewPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserViewPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
