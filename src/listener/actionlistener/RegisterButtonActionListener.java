package listener.actionlistener;

import controller.RegisterController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterButtonActionListener implements ActionListener {

    private RegisterController registerController;

    public RegisterButtonActionListener(RegisterController registerController) {
        this.registerController = registerController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        registerController.displayRegisterDialog();
    }
}
