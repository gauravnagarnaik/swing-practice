package controller;

import model.Request;
import model.Response;
import service.LoginService;
import utils.HostConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInPage {

	private JFrame frmMembershipPortal;
	private JTextField textField;
	private JButton btnSignUp;
	private JPasswordField passwordField;
	private JLabel lblWelcomeToOnline;

	private HostConnection hostConnection;

	private Request request;

	private LoginService loginService;

	/**
	 * Launch the application.
	 */
	public static void launchLoginPage() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInPage window = new LogInPage();
					window.frmMembershipPortal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LogInPage() {
		request = new Request();
		this.loginService = LoginService.getLoginInstance();
		this.hostConnection = HostConnection.getInstance();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMembershipPortal = new JFrame();
		frmMembershipPortal.setResizable(false);
		frmMembershipPortal.setTitle("Membership Portal");
		frmMembershipPortal.setBounds(100, 100, 735, 522);
		frmMembershipPortal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMembershipPortal.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Log In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().isEmpty() || passwordField.getPassword() == null || passwordField.getPassword().length == 0){
					JOptionPane.showMessageDialog(new JFrame(), "User Name or Password cannot be empty",
							"Authentication Error", JOptionPane.ERROR_MESSAGE);
				} else {


					String userPassword = String.copyValueOf(passwordField.getPassword());


					request.setUserName(textField.getText());
					request.setPassword(userPassword);
					request.setMessage("LOGIN");
					hostConnection.setRequest(request);
					try {
						Response response = hostConnection.connect(request);
						if(response == null){
							JOptionPane.showMessageDialog(new JFrame(), "Could not sign in due to Database or Connection errors",
									"Connection Error", JOptionPane.ERROR_MESSAGE);
						}else if(!response.isLoginCheck()){
							JOptionPane.showMessageDialog(new JFrame(), "Invalid UserName or Password! Please try again",
									"Invalid Login", JOptionPane.INFORMATION_MESSAGE);
						} else {
							frmMembershipPortal.setVisible(false);
							if(response.getMember().getIsAdmin()){
								AdminViewPage.launchAdminViewPage();
							} else {
								UserViewPage.launchUserViewPage();
							}
						}


					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(new JFrame(), "Could not establish connection",
								"Connection Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		btnNewButton.setBounds(264, 251, 86, 23);
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setForeground(new Color(0, 0, 255));
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 13));
		frmMembershipPortal.getContentPane().add(btnNewButton);

		textField = new JTextField();
		textField.setBounds(337, 194, 123, 20);
		frmMembershipPortal.getContentPane().add(textField);
		textField.setColumns(10);

		JPasswordField jPasswordField = new JPasswordField();
		jPasswordField.setBounds(146, 76, 86, 20);
		frmMembershipPortal.getContentPane().add(textField);
		jPasswordField.setColumns(10);

		JLabel lblUserName = new JLabel("Username");
		lblUserName.setFont(new Font("Arial", Font.BOLD, 13));
		lblUserName.setBounds(254, 196, 73, 14);
		frmMembershipPortal.getContentPane().add(lblUserName);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Arial", Font.BOLD, 13));
		lblPassword.setBounds(254, 221, 73, 14);
		frmMembershipPortal.getContentPane().add(lblPassword);
		
		btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUpPage.launchSignUpPage();
				frmMembershipPortal.setVisible(false);
			}
		});
		btnSignUp.setForeground(Color.BLUE);
		btnSignUp.setFont(new Font("Arial", Font.BOLD, 13));
		btnSignUp.setBackground(Color.LIGHT_GRAY);
		btnSignUp.setBounds(406, 251, 86, 23);
		frmMembershipPortal.getContentPane().add(btnSignUp);

		passwordField = new JPasswordField();
		passwordField.setBounds(337, 220, 123, 20);
		frmMembershipPortal.getContentPane().add(passwordField);

		lblWelcomeToOnline = new JLabel("Welcome to Online Membership Portal");
		lblWelcomeToOnline.setForeground(Color.BLACK);
		lblWelcomeToOnline.setFont(new Font("Arial", Font.BOLD, 13));
		lblWelcomeToOnline.setBounds(235, 126, 269, 36);
		frmMembershipPortal.getContentPane().add(lblWelcomeToOnline);
	}

}
