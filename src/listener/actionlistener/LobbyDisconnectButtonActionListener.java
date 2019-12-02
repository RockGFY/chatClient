package listener.actionlistener;

import protocol.communication.PacketFactory;
import protocol.entity.UserIP;
import session.SessionManager;
import view.LobbyView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LobbyDisconnectButtonActionListener implements ActionListener {

    private LobbyView view;

    public LobbyDisconnectButtonActionListener(LobbyView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var userIP = UserIP.createUserIP(
                view.getUserNameTextField().getText(),
                null);

        var packet = PacketFactory.createDisconnectRequest(userIP)
                .toString();

        var session = SessionManager.getInstance()
                .getSession(SessionManager.SERVER_NAME);

        session.write(packet);
    }
}
