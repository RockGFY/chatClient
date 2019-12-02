package listener.windowListener;

import communication.SocketChannelConnector;
import config.Configuration;
import util.FileUtils;
import view.LobbyView;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class LobbyWindowListener
        implements WindowListener {

    private LobbyView lobbyView;
    private Configuration config;
    private SocketChannelConnector connector;

    public LobbyWindowListener(LobbyView lobbyView, SocketChannelConnector connector, Configuration config) {
        this.lobbyView = lobbyView;
        this.connector = connector;
        this.config = config;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (connector.isConnected())
            lobbyView.getDisconnectButton().doClick();
        lobbyView.close();

        try {
            FileUtils.writeToConfigFile(
                    Configuration.DEFAULT_DIR + Configuration.REMOTE_SERVER_CONFIG,
                    config);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
