package controller;

import model.Member;
import model.Request;
import model.Response;
import service.MemberService;
import utils.HostConnection;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class AdminViewPage {

	private JFrame frame;
	private MemberService memberService;
	private HostConnection hostConnection;

	/**
	 * Launch the application.
	 */
	public static void launchAdminViewPage() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminViewPage window = new AdminViewPage();
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
	public AdminViewPage() {
		memberService = new MemberService();
		hostConnection = HostConnection.getInstance();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 741, 503);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setForeground(Color.BLACK);
		lblWelcome.setFont(new Font("Arial", Font.BOLD, 13));
		lblWelcome.setBounds(304, 81, 66, 14);
		frame.getContentPane().add(lblWelcome);


		TextArea memberListArea = new TextArea();
		memberListArea.setEditable(false);
		memberListArea.setVisible(false);
		memberListArea.setBounds(78, 26, 596, 404);
		frame.getContentPane().add(memberListArea);


		JButton btnMembersList = new JButton("Members List");
		btnMembersList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Request request = new Request();
				request.setMessage("FETCH_USERS");
				hostConnection.setRequest(request);

				try {
					Response response = hostConnection.connect(request);

					if(response == null){
						JOptionPane.showMessageDialog(new JFrame(), "Could not sign in due to Database or Connection errors",
								"Connection Error", JOptionPane.ERROR_MESSAGE);
					}

					if(response.getMembers() == null || response.getMembers().isEmpty()){
						JOptionPane.showMessageDialog(new JFrame(), "No Users Found",
								"Alert", JOptionPane.INFORMATION_MESSAGE);
					} else {
						memberListArea.setText(response.getMembers().toString());
						memberListArea.setVisible(true);
					}

				/*try {
					List<Member> memberList = memberService.getAllMembers();

					if(memberList == null || memberList.isEmpty()){
						JOptionPane.showMessageDialog(new JFrame(), "No Members Found!",
								"Alert", JOptionPane.INFORMATION_MESSAGE);
					} else {
						memberListArea.setText(memberList.toString());
						memberListArea.setVisible(true);
					}*/
				} catch (Exception ex){
					JOptionPane.showMessageDialog(new JFrame(), "Database error occurred! Please try again",
							"Database Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnMembersList.setForeground(Color.BLUE);
		btnMembersList.setFont(new Font("Arial", Font.BOLD, 13));
		btnMembersList.setBackground(Color.LIGHT_GRAY);
		btnMembersList.setBounds(250, 124, 164, 23);
		frame.getContentPane().add(btnMembersList);
		
		JButton btnManageMembers = new JButton("Delete Members");
		btnManageMembers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DeleteMember.deleteMemberView();
			}
		});
		btnManageMembers.setForeground(Color.BLUE);
		btnManageMembers.setFont(new Font("Arial", Font.BOLD, 13));
		btnManageMembers.setBackground(Color.LIGHT_GRAY);
		btnManageMembers.setBounds(250, 193, 164, 23);
		frame.getContentPane().add(btnManageMembers);
		
		JButton btnUpdateInformation = new JButton("Update Information");
		btnUpdateInformation.setForeground(Color.BLUE);
		btnUpdateInformation.setFont(new Font("Arial", Font.BOLD, 13));
		btnUpdateInformation.setBackground(Color.LIGHT_GRAY);
		btnUpdateInformation.setBounds(250, 227, 164, 23);
		frame.getContentPane().add(btnUpdateInformation);
		
		JButton btnSignOut = new JButton("Sign Out");

		btnSignOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LogInPage.launchLoginPage();
				frame.setVisible(false);
			}
		});
		btnSignOut.setForeground(Color.BLUE);
		btnSignOut.setFont(new Font("Arial", Font.BOLD, 13));
		btnSignOut.setBackground(Color.LIGHT_GRAY);
		btnSignOut.setBounds(283, 261, 96, 23);
		frame.getContentPane().add(btnSignOut);



		JButton btnCloseMemberList = new JButton("Close");
		btnCloseMemberList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				memberListArea.setVisible(false);
			}
		});
		btnCloseMemberList.setBounds(557, 383, 89, 23);
		frame.getContentPane().add(btnCloseMemberList);


	}
}
