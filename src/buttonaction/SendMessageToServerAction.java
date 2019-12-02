package buttonaction;

import protocol.communication.PacketFactory;
import protocol.entity.MessageFactory;
import session.SessionManager;
import view.LobbyView;
import view.MessageView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SendMessageToServerAction
        extends AbstractAction {

    private LobbyView parent; //todo delete in the future.
    private MessageView messageView;

    public SendMessageToServerAction(LobbyView parent, MessageView messageView) {
        this.parent = parent;
        this.messageView = messageView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fromUserName = parent.getUserNameTextField().getText();
        var toUserName = messageView.getPeerNameLabel().getText();
        var content = messageView.getMessageTextField().getText();

        var message = MessageFactory.createPersonalMessage(fromUserName, toUserName, content);
        var packet = PacketFactory.createMessage(message)
                .toString();

        var session = SessionManager.getInstance()
                .getSession(SessionManager.SERVER_NAME);

        session.write(packet);

        messageView.getMessageViewTextArea().append(fromUserName + ": " + content + "\n");
        messageView.getMessageTextField().setText("");
    }
}
