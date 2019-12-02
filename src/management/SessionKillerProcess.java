package management;

import protocol.communication.PacketFactory;
import protocol.entity.UserIP;
import session.Session;
import session.SessionManager;
import util.SystemLogger;
import view.LobbyView;

import java.util.HashSet;
import java.util.TimerTask;

public class SessionKillerProcess
        extends TimerTask {

    private long interval;
    private LobbyView lobbyView;

    SessionKillerProcess(long interval, LobbyView lobbyView) {
        this.interval = interval;
        this.lobbyView = lobbyView;
    }

    @Override
    public void run() {
        var sessionToBeRemoved = new HashSet<Session>();
        var sessionManager = SessionManager.getInstance();

        sessionManager
                .getAllSessions()
                .forEach(session -> {
                    var timeElapsed = session.getTimeElapsed();
                    if (timeElapsed > interval)
                        sessionToBeRemoved.add(session);
                });

        sessionToBeRemoved.stream()
                .filter(session -> !sessionManager.getUser(session).equals(SessionManager.SERVER_NAME))
                .forEach(session -> {
                    sendDisconnectRequest(session);
                    sessionManager.removeSession(session);
                    session.close();
                });

        SystemLogger.info("session has {0} sessions.", sessionManager.getSize());
    }

    private void sendDisconnectRequest(Session session) {
        var myUserName = lobbyView.getUserNameTextField().getText();
        var packet = PacketFactory.createDisconnectFromPeerRequest(
                UserIP.createUserIP(myUserName, null))
                .toString();
        session.write(packet);
    }
}
