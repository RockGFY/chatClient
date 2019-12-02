package listener.keylistener;

import view.LobbyView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MessageTextFieldKeyListener
        implements KeyListener {

    private LobbyView lobbyView;

    public MessageTextFieldKeyListener(LobbyView lobbyView) {
        this.lobbyView = lobbyView;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            lobbyView.getSendMessageButton().doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
