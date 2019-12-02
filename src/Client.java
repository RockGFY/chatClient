import handler.FIFOQueueExecutor;
import handler.ResponseHandler;
import management.SessionKiller;
import communication.SocketChannelConnector;
import config.Configuration;
import controller.LobbyController;
import controller.MessageController;
import controller.RegisterController;
import protocol.entity.User;
import server.AsyncServer;
import socketchannel.ServerSocketChannelFactory;
import util.FileUtils;
import view.LobbyView;
import view.RegisterView;

import java.io.IOException;

public class Client
        implements Runnable {

    private LobbyController lobbyController;

    private Configuration localServerConfig;
    private Configuration remoteServerConfig;

    private SocketChannelConnector socketChannelConnector;
    private ResponseHandler responseHandler;
    private SessionKiller sessionKiller;

    Client() {

        var me = User.createEmptyUser();
        remoteServerConfig = loadConfigurationFromFile(Configuration.REMOTE_SERVER_CONFIG);
        localServerConfig = loadConfigurationFromFile(Configuration.LOCAL_SERVER_CONFIG);
        assert remoteServerConfig != null;

        var lobbyView = new LobbyView();
        var registerDialog = new RegisterView(lobbyView);

        socketChannelConnector = new SocketChannelConnector(localServerConfig);
        var registerController = new RegisterController(registerDialog);
        var messageController = new MessageController(lobbyView, socketChannelConnector);
        lobbyController = new LobbyController(
                lobbyView, socketChannelConnector, registerController, messageController, me, remoteServerConfig);

        responseHandler = new ResponseHandler(socketChannelConnector, lobbyController, messageController);
        sessionKiller = new SessionKiller(lobbyView);

        subscribeToSocketChannel(socketChannelConnector, lobbyView);
    }

    public void run() {

        new AsyncServer(new ServerSocketChannelFactory())
                .setup(localServerConfig)
                .setExecutor(new FIFOQueueExecutor(responseHandler))
                .start();

        lobbyController.displayLobbyFrame();

        sessionKiller.execute();
    }

    private Configuration loadConfigurationFromFile(String fileName) {
        var dir = Configuration.DEFAULT_DIR;
        var path = dir + fileName;
        try {
            FileUtils.createDirectory(dir);
            if (!FileUtils.isExists(path)) {
                var defaultConfig = Configuration.create("localhost", 8060);
                FileUtils.writeToConfigFile(path, defaultConfig);
                return defaultConfig;
            }

            return FileUtils.readFromConfigFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void subscribeToSocketChannel(
            SocketChannelConnector socketChannelConnector,
            LobbyView lobbyView) {
        socketChannelConnector.subscribe(lobbyView.getConnectButton());
        socketChannelConnector.subscribe(lobbyView.getDisconnectButton());
    }
}
