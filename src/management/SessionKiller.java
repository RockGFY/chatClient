package management;

import view.LobbyView;

import java.util.Timer;

public class SessionKiller {

    private final long interval = 1000L * 60L * 2L;
    private LobbyView lobbyView;

    public SessionKiller(LobbyView lobbyView) {
        this.lobbyView = lobbyView;
    }

    public void execute() {
        var repeatedTask = new SessionKillerProcess(interval, lobbyView);

        long delay  = 1000L * 60L * 3L;
        var timer = new Timer("SessionKiller");
        timer.scheduleAtFixedRate(repeatedTask, delay, interval);
    }
}
