package buttonaction;

import protocol.communication.PacketFactory;
import protocol.entity.UserRelationFactory;
import session.SessionManager;
import view.LobbyView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddFriendAction
        extends AbstractAction {

    private LobbyView lobbyView;

    public AddFriendAction(LobbyView lobbyView) {
        super("Add Friend");
        putValue(Action.SHORT_DESCRIPTION, "Add friend");
        this.lobbyView = lobbyView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var requesterName = lobbyView.getUserNameTextField().getText();
        var recipientName = lobbyView.getOnlineUserJList().getSelectedValue();
        var relation = UserRelationFactory.createPendingUserRelation(requesterName, recipientName);
        var packet = PacketFactory.createNewRelationRequest(relation)
                .toString();

        var session = SessionManager.getInstance()
                .getSession(SessionManager.SERVER_NAME);
        session.write(packet);
    }
}
