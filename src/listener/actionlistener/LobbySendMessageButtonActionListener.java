package listener.actionlistener;

import protocol.communication.PacketFactory;
import protocol.entity.MessageFactory;
import session.SessionManager;
import view.LobbyView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LobbySendMessageButtonActionListener implements ActionListener {

    private LobbyView view;

    public LobbySendMessageButtonActionListener(LobbyView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fromUserName = view.getUserNameTextField().getText();
        var content = view.getMessageTextField().getText();

        var message = MessageFactory.createBoardCastMessage(fromUserName, content);

        var packet = PacketFactory.createMessage(message)
                .toString();

        var session = SessionManager.getInstance()
                .getSession(SessionManager.SERVER_NAME);

        session.write(packet);

        view.getMessageTextField().setText("");

    }
}
