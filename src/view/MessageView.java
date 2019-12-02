package view;

import buttonaction.SendMessageToPeerAction;
import buttonaction.SendMessageToServerAction;
import view.component.UpdatableTextArea;
import protocol.entity.UserIP;

import javax.swing.*;
import java.awt.*;

public class MessageView {

    private JDialog messageDialog;

    private JLabel peerNameLabel;
    private JLabel peerAddressLabel;
    private JLabel peerPortLabel;

    private UpdatableTextArea messageViewTextArea;
    private JScrollPane textAreaScrollPane;
    private JTextField messageTextField;
    private JButton sendButton;

    private SendMessageToServerAction toServerAction;
    private SendMessageToPeerAction toPeerAction;

    public MessageView(LobbyView parent, UserIP userIP) {
        var panel = buildPanel(userIP);
        buildDialog(parent.getFrame(), panel);

        toServerAction = new SendMessageToServerAction(parent, this);
        toPeerAction = new SendMessageToPeerAction(parent, this);
    }

    private void buildDialog(JFrame parent, JPanel panel) {
        messageDialog = new JDialog(parent, "Message", false);
        messageDialog.add(panel);
        messageDialog.pack();
    }

    private JPanel buildPanel(UserIP userIP) {
        var panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        peerNameLabel = new JLabel(userIP.getUserName());
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panel.add(peerNameLabel, gridBagConstraints);

        peerAddressLabel = new JLabel(userIP.getAddress());
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        panel.add(peerAddressLabel, gridBagConstraints);

        peerPortLabel = new JLabel("Port: xxxx");
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        panel.add(peerPortLabel, gridBagConstraints);

        messageViewTextArea = new UpdatableTextArea();
        messageViewTextArea.setEditable(false);
        messageViewTextArea.setRows(5);
        messageViewTextArea.setColumns(50);
        textAreaScrollPane = new JScrollPane();
        messageViewTextArea.setFont(new java.awt.Font("MHei", Font.PLAIN, 12));
        textAreaScrollPane.setViewportView(messageViewTextArea);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel.add(textAreaScrollPane, gridBagConstraints);

        messageTextField = new JTextField();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panel.add(messageTextField, gridBagConstraints);

        sendButton = new JButton("Send");
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        panel.add(sendButton, gridBagConstraints);

        return panel;
    }

    public void display() {
        messageDialog.setVisible(true);
    }

    public JLabel getPeerNameLabel() {
        return peerNameLabel;
    }

    public JLabel getPeerAddressLabel() {
        return peerAddressLabel;
    }

    public JTextField getMessageTextField() {
        return messageTextField;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public UpdatableTextArea getMessageViewTextArea() {
        return messageViewTextArea;
    }

    public JDialog getMessageDialog() {
        return messageDialog;
    }

    public void setSendButtonToServer() {
        sendButton.setAction(toServerAction);
    }

    public void setSendButtonToPeer() {
        sendButton.setAction(toPeerAction);
    }
}
