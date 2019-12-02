package view;

import view.component.UpdatableJList;
import view.component.UpdatableTextArea;
import view.component.SubscriberButton;
import view.component.UpdatableListModel;
import view.popupmenu.FriendListPopupMenu;
import view.popupmenu.OnlineUserListPopupMenu;

import javax.swing.*;
import java.awt.*;

public class LobbyView {

    private JFrame lobbyFrame;

    private UpdatableJList onlineUserJList;
    private UpdatableListModel onlineUpdatableListModel;
    private JScrollPane onlineUserListScrollPane;
    private OnlineUserListPopupMenu onlineUserListPopupMenu;

    private UpdatableJList friendJList;
    private UpdatableListModel friendListModel;
    private JScrollPane friendListScrollPane;
    private FriendListPopupMenu friendListPopupMenu;

    private SubscriberButton connectButton;
    private SubscriberButton registerButton;
    private SubscriberButton loginButton;
    private SubscriberButton disconnectButton;
    private JButton sendMessageButton;

    private JLabel hostAddressLabel;
    private JLabel hostPortLabel;
    private JLabel passwordLabel;
    private JLabel userNameLabel;
    private JLabel messageLabel;

    private JPasswordField passwordTextField;
    private UpdatableTextArea updatableTextArea;
    private JScrollPane chatTextAreaScrollPane;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JTextField hostAddressTextField;
    private JTextField hostPortTextField;
    private JTextField userNameTextField;
    private JTextField messageTextField;

    public LobbyView() {
        setLookAndFeel();
        buildLobbyFrame();
    }

    private void buildLobbyFrame() {
        lobbyFrame = new JFrame();
        initUIComponents();
        setupLayout();
        lobbyFrame.pack();
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println("Look & Feel exception");
        }
    }

    private void initUIComponents() {
        lobbyFrame.setTitle("Messenger");
        lobbyFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        hostAddressLabel = new JLabel();
        hostAddressTextField = new JTextField();
        hostPortLabel = new JLabel();
        hostPortTextField = new JTextField();
        connectButton = new SubscriberButton();
        userNameTextField = new JTextField();
        passwordLabel = new JLabel();
        userNameLabel = new JLabel();
        registerButton = new SubscriberButton();
        passwordTextField = new JPasswordField();
        jSeparator1 = new JSeparator();
        chatTextAreaScrollPane = new JScrollPane();
        updatableTextArea = new UpdatableTextArea();

        onlineUserListScrollPane = new JScrollPane();
        onlineUserJList = new UpdatableJList();
        onlineUserJList.setName("OnlineList");
        onlineUserListPopupMenu = OnlineUserListPopupMenu.createMenu();

        friendListScrollPane = new JScrollPane();
        friendJList = new UpdatableJList();
        friendJList.setName("FriendList");
        friendListPopupMenu = FriendListPopupMenu.createMenu();

        messageLabel = new JLabel();
        messageTextField = new JTextField();
        sendMessageButton = new JButton();
        loginButton = new SubscriberButton();
        disconnectButton = new SubscriberButton();
        jSeparator2 = new JSeparator();

        hostAddressTextField.setName("Address");
        hostPortTextField.setName("Port");

        hostAddressLabel.setText("Host Address : ");
        hostPortLabel.setText("Host Port : ");
        connectButton.setText("Connect");
        connectButton.setEnabled(true);
        disconnectButton.setText("Disconnect");
        disconnectButton.setEnabled(false);
        userNameLabel.setText("Username :");
        userNameTextField.setText("bob");
        userNameTextField.setEnabled(false);
        passwordLabel.setText("Password :");
        passwordTextField.setText("1111");
        passwordTextField.setEnabled(false);
        registerButton.setText("SignUp");
        registerButton.setEnabled(false);

        updatableTextArea.setColumns(50);
        updatableTextArea.setFont(new java.awt.Font("MHei", Font.PLAIN, 12));
        updatableTextArea.setRows(5);
        updatableTextArea.setEditable(false);
        chatTextAreaScrollPane.setViewportView(updatableTextArea);

        onlineUserJList.setModel(onlineUpdatableListModel = new UpdatableListModel());
        onlineUserListScrollPane.setViewportView(onlineUserJList);

        friendJList.setModel(friendListModel = new UpdatableListModel());
        friendListScrollPane.setViewportView(friendJList);

        messageLabel.setText("Message : ");

        sendMessageButton.setText("Send Message ");
        sendMessageButton.setEnabled(false);

        loginButton.setText("Login");
        loginButton.setEnabled(false);
    }

    private void setupLayout() {
        var contentPane = lobbyFrame.getContentPane();
        GroupLayout layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator2)
                                        .addComponent(jSeparator1, GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(hostAddressLabel)
                                                        .addComponent(userNameLabel))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(userNameTextField)
                                                                        .addComponent(hostAddressTextField))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(hostPortLabel)
                                                                        .addComponent(passwordLabel))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(hostPortTextField)
                                                                        .addComponent(passwordTextField))))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(connectButton, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                                        )
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(disconnectButton, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(registerButton, GroupLayout.DEFAULT_SIZE, 90, GroupLayout.PREFERRED_SIZE)))))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(friendListScrollPane, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(chatTextAreaScrollPane)
                                                .addGap(18, 18, 18)
                                                .addComponent(onlineUserListScrollPane, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(messageLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(messageTextField)
                                                .addGap(18, 18, 18)
                                                .addComponent(sendMessageButton, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(hostAddressLabel)
                                        .addComponent(hostAddressTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(hostPortLabel)
                                        .addComponent(hostPortTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(connectButton)
                                        .addComponent(disconnectButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(userNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(passwordLabel)
                                        .addComponent(userNameLabel)
                                        .addComponent(registerButton)
                                        .addComponent(passwordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(loginButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(friendListScrollPane, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                                        .addComponent(chatTextAreaScrollPane)
                                        .addComponent(onlineUserListScrollPane, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(sendMessageButton)
                                        .addComponent(messageLabel)
                                        .addComponent(messageTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addContainerGap())
        );
    }

    public SubscriberButton getConnectButton() {
        return connectButton;
    }

    public JButton getSendMessageButton() {
        return sendMessageButton;
    }

    public JTextField getHostAddressTextField() {
        return hostAddressTextField;
    }

    public JTextField getHostPortTextField() {
        return hostPortTextField;
    }

    public SubscriberButton getDisconnectButton() {
        return disconnectButton;
    }

    public JTextField getUserNameTextField() {
        return userNameTextField;
    }

    public JPasswordField getPasswordTextField() {
        return passwordTextField;
    }

    public JTextField getMessageTextField() {
        return messageTextField;
    }

    public UpdatableTextArea getUpdatableTextArea() {
        return updatableTextArea;
    }

    public UpdatableJList getOnlineUserJList() {
        return onlineUserJList;
    }

    public UpdatableJList getFriendJList() {
        return friendJList;
    }

    public FriendListPopupMenu getFriendListPopupMenu() {
        return friendListPopupMenu;
    }

    public OnlineUserListPopupMenu getOnlineUserListPopupMenu() {
        return onlineUserListPopupMenu;
    }

    public UpdatableListModel getOnlineUpdatableListModel() {
        return onlineUpdatableListModel;
    }

    public UpdatableListModel getFriendListModel() {
        return friendListModel;
    }

    public SubscriberButton getLoginButton() {
        return loginButton;
    }

    public SubscriberButton getRegisterButton() {
        return registerButton;
    }

    public JFrame getFrame() {
        return lobbyFrame;
    }

    public void display() {
        lobbyFrame.setVisible(true);
    }

    public void close() {
        lobbyFrame.dispose();
    }
}
