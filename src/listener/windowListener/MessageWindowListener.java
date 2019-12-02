package listener.windowListener;

import management.MessageViewManager;
import view.MessageView;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MessageWindowListener
        implements WindowListener {

    private MessageView messageView;

    public MessageWindowListener(MessageView messageView) {
        this.messageView = messageView;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        MessageViewManager.getInstance()
                .removeMessageView(messageView);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
