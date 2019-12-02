package controller;

import communication.SocketChannelConnector;
import config.Configuration;
import buttonaction.AddFriendAction;
import buttonaction.DeleteFriendAction;
import listener.windowListener.LobbyWindowListener;
import listener.actionlistener.*;
import listener.focuslistener.HostTextFieldFocusListener;
import listener.keylistener.MessageTextFieldKeyListener;
import listener.mouselistener.LobbyUpdatableListMouseListener;
import protocol.entity.User;
import protocol.entity.UserIP;
import protocol.entity.UserRelation;
import protocol.entity.UserRelationStatus;
import view.LobbyView;

import javax.swing.*;

public class LobbyController {

    private LobbyView lobbyView;
    private SocketChannelConnector socketChannelConnector;

    private User me;
    private Configuration config;

    public LobbyController(
            LobbyView lobbyView,
            SocketChannelConnector socketChannelConnector,
            RegisterController registerController,
            MessageController messageController,
            User me,
            Configuration config) {
        this.me = me;
        this.config = config;
        this.lobbyView = lobbyView;
        this.socketChannelConnector = socketChannelConnector;

        setupListeners(registerController, messageController);
        initHostFields();
    }

    public void displayLobbyFrame() {
        lobbyView.display();
    }

    public boolean tryAddToOnlineUserList(UserIP userIP) {
        var myUserName = lobbyView.getUserNameTextField().getText();
        if (!userIP.getUserName().equals(myUserName)) {
            lobbyView.getOnlineUpdatableListModel()
                    .update(userIP);
            return true;
        }
        return false;
    }

    public void removeFromOnlineUserList(UserIP userIP) {
        lobbyView.getOnlineUpdatableListModel()
                .removeElement(userIP.getUserName());
    }

    public void removeFromFriendList(UserIP userIP) {
        lobbyView.getFriendListModel()
                .removeElement(userIP.getUserName());
    }

    public void addToFriendList(UserIP userIP) {
        lobbyView.getFriendListModel()
                .update(userIP);
    }

    public void displayMessage(String message) {
        lobbyView.getUpdatableTextArea()
                .update(message);
    }

    public void clearFriendList() {
        lobbyView.getFriendListModel().clear();
    }

    public void clearOnlineUserList() {
        lobbyView.getOnlineUpdatableListModel().clear();
    }

    public void renderOnlineUserList(String senderName) {
        var onlineUserListModel = lobbyView.getOnlineUpdatableListModel();
        if (onlineUserListModel.contains(senderName))
            lobbyView.getOnlineUserJList().update(senderName);
    }

    public void renderFriendList(String senderName) {
        var friendListModel = lobbyView.getFriendListModel();
        if (friendListModel.contains(senderName))
            lobbyView.getFriendJList().update(senderName);
    }

    public void updateComponentsAfterDisconnect() {
        lobbyView.getConnectButton().setEnabled(true);
        lobbyView.getHostAddressTextField().setEnabled(true);
        lobbyView.getHostPortTextField().setEnabled(true);

        lobbyView.getSendMessageButton().setEnabled(false);
        lobbyView.getMessageTextField().setEnabled(false);
        lobbyView.getDisconnectButton().setEnabled(false);
        lobbyView.getLoginButton().setEnabled(false);
        lobbyView.getRegisterButton().setEnabled(false);
        lobbyView.getUserNameTextField().setEnabled(false);
        lobbyView.getPasswordTextField().setEnabled(false);
    }

    public void updateComponentsAfterLogin() {
        lobbyView.getSendMessageButton().setEnabled(true);
        lobbyView.getLoginButton().setEnabled(false);
        lobbyView.getRegisterButton().setEnabled(false);
        lobbyView.getUserNameTextField().setEnabled(false);
        lobbyView.getPasswordTextField().setEnabled(false);
        lobbyView.getLoginButton()
                .firePropertyChange("isLoggedIn", false, true);
    }

//    public void updateMe() {
//        var myUserName = lobbyView.getUserNameTextField().getText();
//        me.setName(myUserName);
//    }

    public UserRelation promptForFriendDecision(UserIP userIP) {
        int dialogResult = JOptionPane.showConfirmDialog(
                lobbyView.getFrame(), userIP.getUserName() + " wants to be your friend.", "Friendship", JOptionPane.YES_NO_OPTION);

        var decision = dialogResult == 0
                ? UserRelationStatus.ACCEPTED
                : UserRelationStatus.REJECTED;

        return UserRelation.createUserRelation(
                userIP.getUserName(),
                lobbyView.getUserNameTextField().getText(),
                decision);
    }

    private void initHostFields() {
        lobbyView.getHostAddressTextField().setText(config.getHostName());
        lobbyView.getHostPortTextField().setText(String.valueOf(config.getPort()));
    }

    //region Setup view.component listeners.
    private void setupListeners(RegisterController registerController, MessageController messageController) {
        var updatableListMouseListener = new LobbyUpdatableListMouseListener(lobbyView, messageController, socketChannelConnector);
        setupWindowsListener();
        setupConnectButton();
        setupRegisterButton(registerController);
        setupLoginButton();
        setupDisconnectButton();
        setupSendMessageButton();
        setupMessageTextField();
        setupOnlineUserList(updatableListMouseListener);
        setupFriendList(updatableListMouseListener);
        setupAddressTextField();
        setupPortTextField();
    }

    private void setupWindowsListener() {
        lobbyView.getFrame()
                .addWindowListener(new LobbyWindowListener(lobbyView, socketChannelConnector, config));
    }

    private void setupConnectButton() {
        lobbyView.getConnectButton()
                .addPropertyChangeListener("connected", evt -> {
                    lobbyView.getConnectButton().setEnabled(false);
                    lobbyView.getDisconnectButton().setEnabled(true);
                    lobbyView.getHostAddressTextField().setEnabled(false);
                    lobbyView.getHostPortTextField().setEnabled(false);

                    lobbyView.getLoginButton().setEnabled(true);
                    lobbyView.getRegisterButton().setEnabled(true);
                    lobbyView.getUserNameTextField().setEnabled(true);
                    lobbyView.getPasswordTextField().setEnabled(true);
                });

        lobbyView.getConnectButton()
                .addActionListener(new LobbyConnectButtonActionListener(lobbyView, socketChannelConnector));
    }

    private void setupRegisterButton(RegisterController registerController) {
        lobbyView.getRegisterButton()
                .addActionListener(new RegisterButtonActionListener(registerController));
    }

    private void setupLoginButton() {
        lobbyView.getLoginButton()
                .addActionListener(new LobbyLoginButtonActionListener(lobbyView));

        lobbyView.getLoginButton()
                .addPropertyChangeListener("isLoggedIn", evt -> {
                    var state = (Boolean) evt.getNewValue();

                    if (!state)
                        lobbyView.getFriendListModel().clear();
                });
    }

    private void setupDisconnectButton() {
        lobbyView.getDisconnectButton()
                .addPropertyChangeListener("disconnected", evt -> {
                    lobbyView.getConnectButton().setEnabled(true);
                    lobbyView.getDisconnectButton().setEnabled(false);
                    lobbyView.getLoginButton().setEnabled(false);
                    lobbyView.getSendMessageButton().setEnabled(false);

                    lobbyView.getLoginButton()
                            .firePropertyChange("isLoggedIn", true, false);
                });

        lobbyView.getDisconnectButton()
                .addActionListener(new LobbyDisconnectButtonActionListener(lobbyView));
    }

    private void setupSendMessageButton() {
        lobbyView.getSendMessageButton()
                .addActionListener(new LobbySendMessageButtonActionListener(lobbyView));
    }

    private void setupMessageTextField() {
        lobbyView.getMessageTextField()
                .addKeyListener(new MessageTextFieldKeyListener(lobbyView));
    }

    private void setupOnlineUserList(LobbyUpdatableListMouseListener updatableListMouseListener) {
        lobbyView.getOnlineUserJList()
                .addMouseListener(updatableListMouseListener);
        lobbyView.getOnlineUserListPopupMenu()
                .setAddFriendAction(new AddFriendAction(lobbyView));
    }

    private void setupFriendList(LobbyUpdatableListMouseListener updatableListMouseListener) {
        lobbyView.getFriendJList()
                .addMouseListener(updatableListMouseListener);
        lobbyView.getFriendListPopupMenu()
                .setDeleteFriendAction(new DeleteFriendAction(lobbyView));
    }

    private void setupAddressTextField() {
        lobbyView.getHostAddressTextField()
                .addFocusListener(new HostTextFieldFocusListener(lobbyView, config));
    }

    private void setupPortTextField() {
        lobbyView.getHostPortTextField()
                .addFocusListener(new HostTextFieldFocusListener(lobbyView, config));
    }

    //endregion
}
