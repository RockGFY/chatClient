package listener.mouselistener;

import communication.SocketChannelConnector;
import config.Configuration;
import protocol.entity.User;
import view.component.UpdatableJList;
import controller.MessageController;
import management.MessageViewManager;
import protocol.entity.UserIP;
import session.SessionManager;
import view.LobbyView;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LobbyUpdatableListMouseListener
        implements MouseListener {

    private LobbyView lobbyView;
    private MessageController messageController;
    private SocketChannelConnector socketChannelConnector;

    public LobbyUpdatableListMouseListener(LobbyView lobbyView, MessageController messageController, SocketChannelConnector socketChannelConnector) {
        this.lobbyView = lobbyView;
        this.messageController = messageController;
        this.socketChannelConnector = socketChannelConnector;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        var source = (UpdatableJList) e.getSource();

        if (SwingUtilities.isRightMouseButton(e))
            source.setSelectedIndex(source.locationToIndex(e.getPoint()));

        if (source.getSelectedIndex() < 0)
            return;

        if (e.getClickCount() == 2) {
            var userName = (String) source.getSelectedValue();
            var address = messageController.getUserIP(userName);
            var toUserIP = UserIP.createUserIP(userName, address);

            var messageView = messageController.createMessageView(toUserIP);

            MessageViewManager.getInstance()
                    .add(userName, messageView);

            messageView.display();

            var unreadMessages = messageController.retrieveUnreadMessages(toUserIP);
            messageController.displayUnreadMessages(unreadMessages, messageView);
            messageController.deleteUnreadMessages(userName);

            source.unBold(userName);
            if (lobbyView.getFriendListModel().contains(userName)) {
                lobbyView.getFriendJList().unBold(userName);
            }

            if (!SessionManager.getInstance().containsUser(userName)) {
                SwingUtilities.invokeLater(
                        () -> {
                            var myUserName = lobbyView.getUserNameTextField().getText();
                            var myAddress = socketChannelConnector.getLocalAddress();
                            var fromUserIP = UserIP.createUserIP(myUserName, myAddress);
                            if (messageController.tryToConnectToPeer(fromUserIP, toUserIP))
                                messageView.setSendButtonToPeer();
                        }
                );
            } else {
                messageView.setSendButtonToPeer();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        var source = (JList) e.getSource();
        if (!SwingUtilities.isRightMouseButton(e)
                || source.isSelectionEmpty()
                || source.locationToIndex(e.getPoint()) != source.getSelectedIndex())
            return;

        switch (source.getName()) {
            case "OnlineList":
                lobbyView.getOnlineUserListPopupMenu()
                        .show(source, e.getX(), e.getY());
                break;
            case "FriendList":
                lobbyView.getFriendListPopupMenu()
                        .show(source, e.getX(), e.getY());
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
