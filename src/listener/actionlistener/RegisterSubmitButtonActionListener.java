package listener.actionlistener;

import protocol.communication.PacketFactory;
import protocol.entity.User;
import session.SessionManager;
import view.RegisterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterSubmitButtonActionListener implements ActionListener {

    private RegisterView registerView;

    public RegisterSubmitButtonActionListener(RegisterView registerView) {
        this.registerView = registerView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var userName = registerView.getRegisterNameTextField().getText();
        var password = registerView.getRegisterPasswordTextField().getPassword();
        var user = User.createUser(userName, String.valueOf(password));

        var packet = PacketFactory.createRegisterRequest(user)
                .toString();

        var session = SessionManager.getInstance()
                .getSession(SessionManager.SERVER_NAME);

        session.write(packet);

        resetDialog();
    }

    private void resetDialog() {
        registerView.getRegisterNameTextField().setText("");
        registerView.getRegisterPasswordTextField().setText("");
        registerView.close();
    }
}
