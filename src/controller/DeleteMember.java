package controller;

import model.Request;
import model.Response;
import utils.HostConnection;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class DeleteMember extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField textMemberId;

    private HostConnection hostConnection;


    /**
     * Launch the application.
     */
    public static void deleteMemberView() {
        try {
            DeleteMember dialog = new DeleteMember();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public DeleteMember() {

        hostConnection = HostConnection.getInstance();

        setTitle("Online Membership Portal - Delete Member");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JLabel lblMemberId = new JLabel("Enter MemberId ");
            lblMemberId.setBounds(71, 88, 105, 14);
            contentPanel.add(lblMemberId);
        }
        {
            textMemberId = new JTextField();
            textMemberId.setBounds(186, 85, 161, 20);
            contentPanel.add(textMemberId);
            textMemberId.setColumns(10);
        }
        {
            JLabel lblNewLabel = new JLabel("Delete Member");
            lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
            lblNewLabel.setBounds(169, 29, 178, 32);
            contentPanel.add(lblNewLabel);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        Request request = new Request();
                        request.setUserName(textMemberId.getText());
                        request.setMessage("DELETE");
                        hostConnection.setRequest(request);

                        try {
                            Response response = hostConnection.connect(request);
                            if(response == null){
                                JOptionPane.showMessageDialog(new JFrame(), "Could not sign in due to Database or Connection errors",
                                        "Connection Error", JOptionPane.ERROR_MESSAGE);
                            }

                            if(!response.isLoginCheck()){
                                JOptionPane.showMessageDialog(new JFrame(), "Invalid UserName or Password! Please try again",
                                        "Invalid Login", JOptionPane.INFORMATION_MESSAGE);
                            }




                        } catch (IOException e1) {
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(new JFrame(), "Could not establish connection",
                                    "Connection Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        contentPanel.setVisible(false);
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }

}
