package controller;

import listener.actionlistener.RegisterSubmitButtonActionListener;
import view.RegisterView;

public class RegisterController {

    private RegisterView registerView;

    public RegisterController(RegisterView registerView) {
        this.registerView = registerView;

        setupRegisterSubmitButton();
    }

    public void displayRegisterDialog() {
        registerView.display();
    }

    private void setupRegisterSubmitButton() {
        registerView.getRegisterSubmitButton()
                .addActionListener(new RegisterSubmitButtonActionListener(registerView));
    }
}
