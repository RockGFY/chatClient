package listener.actionlistener;

import communication.SocketChannelConnector;
import view.LobbyView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LobbyConnectButtonActionListener implements ActionListener {

    private LobbyView view;
    private SocketChannelConnector connector;

    public LobbyConnectButtonActionListener(LobbyView view, SocketChannelConnector connector) {
        this.view = view;
        this.connector = connector;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var serverAddress = view.getHostAddressTextField();
        var serverPort = view.getHostPortTextField();

        connector.tryConnectToServer(
                serverAddress.getText(),
                Integer.parseInt(serverPort.getText()));
    }
}
