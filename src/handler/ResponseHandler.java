package handler;

import communication.SocketChannelConnector;
import controller.LobbyController;
import controller.MessageController;
import dao.DataSourceClient;
import management.MessageViewManager;
import protocol.communication.Packet;
import protocol.communication.PacketFactory;
import queue.Bundle;
import session.SessionManager;
import util.DataParser;

public class ResponseHandler {

    private LobbyController lobbyController;
    private MessageController messageController;
    private SocketChannelConnector socketChannelConnector;

    public ResponseHandler(SocketChannelConnector socketChannelConnector,
                           LobbyController lobbyController,
                           MessageController messageController) {
        this.lobbyController = lobbyController;
        this.messageController = messageController;
        this.socketChannelConnector = socketChannelConnector;
    }

    void handle(Bundle bundle) {
        var packet = bundle.getPacket();

        switch (packet.getType()) {
            case REGISTER:
                updateViewFromRegistrationResponse(packet);
                break;
            case LOGIN:
                updateViewFromLoginResponse(packet);
                break;
            case LOGIN_FROM_PEER:
                handleLoginFromPeer(bundle);
                break;
            case DISCONNECT:
                updateViewFromDisconnectResponse(packet);
                break;
            case DISCONNECT_FROM_PEER:
                handleDisconnectFromPeer(packet);
                break;
            case DISCONNECT_NOTIFICATION:
                updateViewFromDisconnectNotification(packet);
                break;
            case MESSAGE:
                updateViewFromMessageResponse(packet);
                break;
            case NEW_USER_NOTIFICATION:
                updateViewFromNewUserResponse(packet);
                break;
            case GET_RELATIONS:
                updateViewFromGetFriendsResponse(packet);
                break;
            case PENDING_RELATION_REQUESTS:
                updateViewFromPendingFriendRequestsResponse(packet);
                break;
            case NEW_RELATION_NOTIFICATION:
                updateViewFromNewRelationNotification(packet);
                break;
            case DELETE_RELATION_NOTIFICATION:
                updateViewFromDeleteRelationNotification(packet);
                break;
        }
    }

    private void handleLoginFromPeer(Bundle bundle) {
        var session = bundle.getSession();
        var userIP = DataParser.getUserIP(bundle.getPacket().getAttachment());

        SessionManager.getInstance()
                .addSession(userIP.getUserName(), session);

        DataSourceClient.getInstance().addOrUpdateUserIP(userIP);
    }

    private void handleDisconnectFromPeer(Packet packet) {
        var userIP = DataParser.getUserIP(packet.getAttachment());
        var sessionManager = SessionManager.getInstance();

        sessionManager
                .getSession(userIP.getUserName())
                .close();

        sessionManager
                .removeSession(userIP.getUserName());

        messageController.setSendButtonActionToServer(userIP.getUserName());

        lobbyController.displayMessage("%s has been disconnected.");
    }

    private void updateViewFromNewRelationNotification(Packet packet) {
        var userIP = DataParser.getUserIP(packet.getAttachment());

        lobbyController.addToFriendList(userIP);
        lobbyController.displayMessage(
                String.format("[ Server ] : %s is now your friend.", userIP.getUserName()));
    }

    private void updateViewFromDeleteRelationNotification(Packet packet) {
        var userIP = DataParser.getUserIP(packet.getAttachment());

        lobbyController.removeFromFriendList(userIP);
        lobbyController.displayMessage(
                String.format("[ Server ] : %s is no longer your homie.", userIP.getUserName()));
    }

    private void updateViewFromPendingFriendRequestsResponse(Packet packet) {
        var userList = DataParser.getUserIPList(packet.getAttachment());

        // todo temporary! needs to integrate with client UI later.
        for (var userIP : userList) {
            var newUserRelation = lobbyController.promptForFriendDecision(userIP);
            var newPacket = PacketFactory.createNewRelationRequest(newUserRelation)
                    .toString();

            var session = SessionManager.getInstance()
                    .getSession(SessionManager.SERVER_NAME);

            session.write(newPacket);
        }
    }

    private void updateViewFromDisconnectResponse(Packet packet) {
        var message = packet.getErrorMessage();
        if (message.isEmpty()) {
            message = "[ Server ] : You have left the room.";
            socketChannelConnector.close();
        }

        lobbyController.displayMessage(message);
        lobbyController.clearFriendList();
        lobbyController.clearOnlineUserList();
        lobbyController.updateComponentsAfterDisconnect();
    }

    private void updateViewFromDisconnectNotification(Packet packet) {
        var userIP = DataParser.getUserIP(packet.getAttachment());

        lobbyController.displayMessage(
                String.format("[ Server ] : %s has left the room.", userIP.getUserName()));

        lobbyController.removeFromOnlineUserList(userIP);
    }

    private void updateViewFromNewUserResponse(Packet packet) {
        var userIP = DataParser.getUserIP(packet.getAttachment());
        if (lobbyController.tryAddToOnlineUserList(userIP))
            DataSourceClient.getInstance().addOrUpdateUserIP(userIP);

        lobbyController.displayMessage(
                String.format("[ Server ] : Welcome %s the chat room...", userIP.getUserName()));
    }

    private void updateViewFromMessageResponse(Packet packet) {
        var message = DataParser.getMessage(packet.getAttachment());

        var messageStr = String.format("[ %s ] : %s",
                                        message.getSenderId(),
                                        message.getContent());

        // todo checking null is too ugly
        if (message.getRecipientId() == null) {
            lobbyController.displayMessage(messageStr);

        } else {
            var senderName = message.getSenderId();
            var senderMessageView = MessageViewManager.getInstance().getMessageView(senderName);

            if (senderMessageView != null) {
                messageController.displayMessage(senderMessageView, messageStr);
            }
            else {
                DataSourceClient.getInstance().addUnreadMessages(message);
                lobbyController.renderOnlineUserList(senderName);
                lobbyController.renderFriendList(senderName);
            }
        }
    }

    private void updateViewFromLoginResponse(Packet packet) {
        var message = "";
        if (packet.getErrorMessage().isEmpty()) {
            var userList = DataParser.getUserIPList(packet.getAttachment());

            userList.forEach(userIP -> {
                lobbyController.tryAddToOnlineUserList(userIP);
                DataSourceClient.getInstance().addOrUpdateUserIP(userIP);
            });

            message = "You are now log in.";

            lobbyController.updateComponentsAfterLogin();
            //lobbyController.updateMe();
        } else {
            message = String.format("[ Server ] : %s", packet.getErrorMessage());
        }
        lobbyController.displayMessage(message);
    }

    private void updateViewFromRegistrationResponse(Packet packet) {
        String message = packet.getErrorMessage().isEmpty()
                ? "[ Server ] : Register successfully."
                : String.format("[ Server ] : %s",
                packet.getErrorMessage());

        lobbyController.displayMessage(message);
    }

    private void updateViewFromGetFriendsResponse(Packet packet) {
        var userList = DataParser.getUserIPList(packet.getAttachment());
        userList.forEach(userIP -> lobbyController.addToFriendList(userIP));
    }

}
