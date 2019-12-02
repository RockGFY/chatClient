package listener.focuslistener;

import config.Configuration;
import view.LobbyView;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HostTextFieldFocusListener
        implements FocusListener {

    private LobbyView lobbyView;
    private Configuration config;

    public HostTextFieldFocusListener(LobbyView lobbyView, Configuration config) {
        this.lobbyView = lobbyView;
        this.config = config;
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        var textField = (JTextField)e.getSource();
        var fieldName = textField.getName();

        switch (fieldName) {
            case "Address":
                config.setHostName(textField.getText());
                break;
            case "Port":
                config.setPort(Integer.parseInt(textField.getText()));
                break;
        }
    }
}
