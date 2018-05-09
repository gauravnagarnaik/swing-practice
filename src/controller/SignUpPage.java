package controller;

import model.Login_Info;
import model.Member;
import model.Request;
import model.Response;
import service.LoginService;
import service.MemberService;
import utils.HostConnection;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.IOException;

public class SignUpPage {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JPasswordField passwordField;

	private HostConnection hostConnection;

	/**
	 * Launch the application.
	 */
	public static void launchSignUpPage() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpPage window = new SignUpPage();
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
	public SignUpPage() {
		initialize();
		this.hostConnection = HostConnection.getInstance();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 742, 504);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("");
		label.setFont(new Font("Arial", Font.BOLD, 13));
		label.setBounds(43, 26, 73, 14);
		frame.getContentPane().add(label);

		JLabel lblNewMember = new JLabel("New Member");
		lblNewMember.setForeground(Color.BLACK);
		lblNewMember.setFont(new Font("Arial", Font.BOLD, 13));
		lblNewMember.setBounds(342, 55, 103, 14);
		frame.getContentPane().add(lblNewMember);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Arial", Font.BOLD, 13));
		lblFirstName.setBounds(208, 95, 73, 14);
		frame.getContentPane().add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Arial", Font.BOLD, 13));
		lblLastName.setBounds(208, 120, 73, 14);
		frame.getContentPane().add(lblLastName);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Arial", Font.BOLD, 13));
		lblAddress.setBounds(208, 145, 73, 14);
		frame.getContentPane().add(lblAddress);

		JLabel lblContact = new JLabel("Contact");
		lblContact.setFont(new Font("Arial", Font.BOLD, 13));
		lblContact.setBounds(208, 170, 73, 14);
		frame.getContentPane().add(lblContact);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Arial", Font.BOLD, 13));
		lblUsername.setBounds(208, 195, 73, 14);
		frame.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Arial", Font.BOLD, 13));
		lblPassword.setBounds(208, 220, 73, 14);
		frame.getContentPane().add(lblPassword);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(291, 93, 190, 20);
		frame.getContentPane().add(textField);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(291, 118, 190, 20);
		frame.getContentPane().add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(291, 143, 190, 20);
		frame.getContentPane().add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(291, 168, 190, 20);
		frame.getContentPane().add(textField_3);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(291, 193, 190, 20);
		frame.getContentPane().add(textField_4);

		passwordField = new JPasswordField();
		passwordField.setBounds(291, 218, 190, 20);
		frame.getContentPane().add(passwordField);

		JCheckBox addAsAdmin = new JCheckBox("Add as Admin");
		addAsAdmin.setBounds(331, 245, 128, 23);
		frame.getContentPane().add(addAsAdmin);
		
		JButton button = new JButton("Sign Up");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isValidInput()){
					JOptionPane.showMessageDialog(new JFrame(), "Please enter all the information",
							"All fields necessary", JOptionPane.ERROR_MESSAGE);
				} else {
					String userPassword = String.copyValueOf(passwordField.getPassword());

					Request request = new Request();
					try {


						request.setFirstName(textField.getText());
						request.setLastName(textField_1.getText());
						request.setAddress(textField_2.getText());
						request.setPhoneNumber(textField_3.getText());
						request.setUserName(textField_4.getText());
						request.setPassword(userPassword);
						request.setMessage("REGISTER");

						if(addAsAdmin.isSelected()) {
							request.setAdmin(true);
						} else {
							request.setAdmin(false);
						}

					 	hostConnection.setRequest(request);
						try {
							Response response = hostConnection.connect(request);

							JOptionPane.showMessageDialog(new JFrame(), "Member Added Successfully",
									"Success", JOptionPane.INFORMATION_MESSAGE);

						} catch (Exception e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(new JFrame(), "Could not establish connection",
									"Connection Error", JOptionPane.ERROR_MESSAGE);
						}

						frame.setVisible(false);
						LogInPage.launchLoginPage();


					/*Member newMember = new Member();
					Login_Info login_info = new Login_Info();

					newMember.setFirstName(textField.getText());
					newMember.setLastName(textField_1.getText());
					newMember.setAddress(textField_2.getText());
					newMember.setPhoneNumber(textField_3.getText());

					login_info.setUserName(textField_4.getText());
					login_info.setPassword(userPassword);

					try {
						if(addAsAdmin.isSelected()) {
							newMember.setIsAdmin(true);
							memberService.addMember(newMember, login_info);
						} else {
							newMember.setIsAdmin(false);
							memberService.addMember(newMember, login_info);

						}
						JOptionPane.showMessageDialog(new JFrame(), "Member Added Successfully",
								"Success", JOptionPane.INFORMATION_MESSAGE);

						SignUpPage.launchSignUpPage();
						frame.setVisible(false);*/
					} catch (Exception ex){
						JOptionPane.showMessageDialog(new JFrame(), "Database error occurred! Please try again",
								"Database Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			private boolean isValidInput() {
				return textField.getText().isEmpty() || passwordField.getPassword() == null || passwordField.getPassword().length == 0 || textField_1.getText().isEmpty() || textField_2.getText().isEmpty() || textField_3.getText().isEmpty() || textField_4.getText().isEmpty();
			}
		});
		button.setForeground(Color.BLUE);
		button.setFont(new Font("Arial", Font.BOLD, 13));
		button.setBackground(Color.LIGHT_GRAY);
		button.setBounds(342, 286, 86, 23);
		frame.getContentPane().add(button);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogInPage.launchLoginPage();
				frame.setVisible(false);
			}
		});
		btnBack.setForeground(Color.BLUE);
		btnBack.setFont(new Font("Arial", Font.BOLD, 13));
		btnBack.setBackground(Color.LIGHT_GRAY);
		btnBack.setBounds(342, 320, 86, 23);
		frame.getContentPane().add(btnBack);



	}
}
