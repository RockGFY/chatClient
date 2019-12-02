package buttonaction;

import protocol.communication.PacketFactory;
import protocol.entity.MessageFactory;
import session.SessionManager;
import view.LobbyView;
import view.MessageView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SendMessageToPeerAction
        extends AbstractAction {

    private MessageView messageView;
    private LobbyView lobbyView;

    public SendMessageToPeerAction(LobbyView lobbyView, MessageView messageView) {
        this.messageView = messageView;
        this.lobbyView = lobbyView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fromUserName = lobbyView.getUserNameTextField().getText();
        var toUserName = messageView.getPeerNameLabel().getText();
        var content = messageView.getMessageTextField().getText();

        var message = MessageFactory.createPersonalMessage(fromUserName, toUserName, content);
        var packet = PacketFactory.createMessage(message)
                .toString();

        var session = SessionManager.getInstance()
                .getSession(toUserName);

        session.write(packet);
        messageView.getMessageViewTextArea().append(fromUserName + ": " + content + "\n");
        messageView.getMessageTextField().setText("");
    }
}
