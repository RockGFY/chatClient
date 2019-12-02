package controller;

import communication.SocketChannelConnector;
import listener.windowListener.MessageWindowListener;
import dao.DataSourceClient;
import management.MessageViewManager;
import protocol.entity.UserIP;
import view.LobbyView;
import view.MessageView;

import java.util.List;

public class MessageController {

    private LobbyView parentView;
    private SocketChannelConnector socketChannelConnector;

    public MessageController(LobbyView parentView, SocketChannelConnector socketChannelConnector) {
        this.parentView = parentView;
        this.socketChannelConnector = socketChannelConnector;
    }

    public MessageView createMessageView(UserIP userIP) {
        var messageView = new MessageView(parentView, userIP);

        messageView.setSendButtonToServer();

        setupWindowsListener(messageView);

        return messageView;
    }

    public boolean tryToConnectToPeer(UserIP fromUserIP, UserIP toUserIP) {
        if (socketChannelConnector
                .tryConnectToPeer(
                        toUserIP.getUserName(),
                        toUserIP.getAddress(),
                        8060)) {
            socketChannelConnector.tryLoginToPeer(fromUserIP, toUserIP);
            return true;
        }
        return false;
    }

    public String getUserIP(String userName) {
        return DataSourceClient.getInstance().getUserIP(userName);
    }

    public List<String> retrieveUnreadMessages(UserIP userIP) {
        return DataSourceClient.getInstance().getUnreadMessages(userIP.getUserName());
    }

    public void displayUnreadMessages(List<String> messages, MessageView messageView) {
        var messageTextArea = messageView.getMessageViewTextArea();
        messages.forEach(messageTextArea::update);
    }

    public void deleteUnreadMessages(String username) {
        DataSourceClient.getInstance().deleteUnreadMessages(username);
    }

    public void setSendButtonActionToServer(String username) {
        var messageView = MessageViewManager.getInstance().getMessageView(username);
        if (messageView != null){
            messageView.setSendButtonToServer();
        }
    }

    public void setSendButtonActionToPeer(String username) {
        var messageView = MessageViewManager.getInstance().getMessageView(username);
        messageView.setSendButtonToPeer();
    }

    private void setupWindowsListener(MessageView messageView) {
        messageView.getMessageDialog()
                .addWindowListener(new MessageWindowListener(messageView));
    }

    public void displayMessage(MessageView messageView, String message) {
        messageView.getMessageViewTextArea().update(message);
    }
}
