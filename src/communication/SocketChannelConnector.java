package communication;

import config.Configuration;
import protocol.communication.AbstractPacket;
import protocol.communication.PacketFactory;
import protocol.entity.UserIP;
import session.Session;
import session.SessionManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class SocketChannelConnector {

    private AsynchronousSocketChannel socketChannel;
    private boolean isConnected = false;

    private SubmissionPublisher<ConnectionStatus> publisher = new SubmissionPublisher<>();

    private Configuration localConfig; // todo need to remove in the future.

    public SocketChannelConnector(Configuration localConfig) {
        this.localConfig = localConfig;
    }

    public boolean tryConnectToServer(String serverAddress, int serverPort) {
        if (isConnected)
            return true;

        try {
            socketChannel = AsynchronousSocketChannel.open();

            var result = socketChannel.connect(
                    new InetSocketAddress(serverAddress, serverPort));

            isConnected = result.get() == null;

            if (isConnected) {
                var session = Session.createSession(socketChannel);
                SessionManager.getInstance()
                        .addSession(SessionManager.SERVER_NAME, session);
                session.read(ByteBuffer.allocate(AbstractPacket.MAX_SIZE));

                publisher.submit(ConnectionStatus.CONNECTED);
            }

        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return isConnected;
    }

    public boolean tryConnectToPeer(String userName, String address, int port) {
        try {
            socketChannel = AsynchronousSocketChannel.open();

            var result = socketChannel.connect(
                    new InetSocketAddress(address, port));

            isConnected = result.get() == null;

            if (isConnected) {
                var session = Session.createSession(socketChannel);
                SessionManager.getInstance().addSession(userName, session);
                session.read(ByteBuffer.allocate(AbstractPacket.MAX_SIZE));
                return true;
            }

        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void tryLoginToPeer(UserIP fromUserIP, UserIP toUserIP) {
        var loginPacket =
                PacketFactory.createLoginFromPeerRequest(
                        UserIP.createUserIP(fromUserIP.getUserName(), fromUserIP.getAddress())
                ).toString();

        SessionManager.getInstance()
                .getSession(toUserIP.getUserName())
                .write(loginPacket);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public String getLocalAddress() {
        return localConfig.getHostName();
    }

    public void close() {
        publisher.submit(ConnectionStatus.DISCONNECTED);
        try {
            isConnected = false;
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(Flow.Subscriber<ConnectionStatus> subscriber) {
        publisher.subscribe(subscriber);
    }
}
