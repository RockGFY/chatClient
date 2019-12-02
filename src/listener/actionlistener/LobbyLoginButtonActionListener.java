package listener.actionlistener;

import protocol.communication.PacketFactory;
import protocol.entity.User;
import session.SessionManager;
import view.LobbyView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LobbyLoginButtonActionListener implements ActionListener {

    private LobbyView view;

    public LobbyLoginButtonActionListener(LobbyView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var userName = view.getUserNameTextField().getText();
        var password = view.getPasswordTextField().getPassword();
        var user = User.createUser(userName, String.valueOf(password));

        var packet = PacketFactory.createLoginRequest(user)
                .toString();

        var session = SessionManager.getInstance()
                .getSession(SessionManager.SERVER_NAME);

        session.write(packet);
    }
}
