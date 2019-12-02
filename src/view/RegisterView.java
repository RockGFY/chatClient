package view;

import javax.swing.*;
import java.awt.*;

public class RegisterView {

    private JDialog registerDialog;

    private JTextField registerNameTextField;
    private JPasswordField registerPasswordTextField;
    private JButton registerSubmitButton;

    public RegisterView(LobbyView parentView) {
        var panel = buildPanel();
        buildDialog(parentView.getFrame(), panel);
    }

    private void buildDialog(JFrame parent, JPanel panel) {
        registerDialog = new JDialog(parent, "Register", false);
        registerDialog.setPreferredSize(new Dimension(400, 120));
        registerDialog.setLocationRelativeTo(parent);
        registerDialog.add(panel);
        registerDialog.pack();
    }

    private JPanel buildPanel() {
        var registerNameLabel = new JLabel("Name: ");
        registerNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        registerNameLabel.setSize(100, 20);
        registerNameLabel.setLocation(100, 100);

        registerNameTextField = new JTextField();
        registerNameTextField.setFont(new Font("Arial", Font.PLAIN, 15));
        registerNameTextField.setSize(190, 20);
        registerNameTextField.setLocation(200, 100);

        var registerPasswordLabel = new JLabel("Password: ");
        registerPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        registerPasswordLabel.setSize(100, 20);
        registerPasswordLabel.setLocation(100, 150);

        registerPasswordTextField = new JPasswordField();
        registerPasswordTextField.setFont(new Font("Arial", Font.PLAIN, 15));
        registerPasswordTextField.setSize(150, 20);
        registerPasswordTextField.setLocation(200, 150);

        registerSubmitButton = new JButton("Submit");
        registerSubmitButton.setFont(new Font("Arial", Font.PLAIN, 15));
        registerSubmitButton.setSize(100, 20);
        registerSubmitButton.setLocation(150, 450);

        JTextArea displayResult = new JTextArea();
        displayResult.setFont(new Font("Arial", Font.PLAIN, 15));
        displayResult.setSize(200, 75);
        displayResult.setEditable(false);
        displayResult.setLocation(580, 175);
        displayResult.setLineWrap(true);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        panel.add(registerNameLabel);
        panel.add(registerNameTextField);

        panel.add(registerPasswordLabel);
        panel.add(registerPasswordTextField);

        panel.add(registerSubmitButton);
        panel.add(displayResult);

        return panel;
    }

    public JTextField getRegisterNameTextField() {
        return registerNameTextField;
    }

    public JPasswordField getRegisterPasswordTextField() {
        return registerPasswordTextField;
    }

    public JButton getRegisterSubmitButton() {
        return registerSubmitButton;
    }

    public void display() {
        registerDialog.setVisible(true);
    }

    public void close() {
        registerDialog.dispose();
    }
}
