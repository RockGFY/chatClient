package buttonaction;

import protocol.communication.PacketFactory;
import protocol.entity.UserRelationFactory;
import session.SessionManager;
import view.LobbyView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteFriendAction
        extends AbstractAction {

    private LobbyView lobbyView;

    public DeleteFriendAction(LobbyView lobbyView) {
        super("Delete Friend");
        putValue(Action.SHORT_DESCRIPTION, "Delete friend");
        this.lobbyView = lobbyView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var requesterName = lobbyView.getUserNameTextField().getText();
        var recipientName = lobbyView.getFriendJList().getSelectedValue();
        var relation = UserRelationFactory.createDeletedUserRelation(requesterName, recipientName);
        var packet = PacketFactory.createDeleteRelationRequest(relation)
                .toString();

        var session = SessionManager.getInstance()
                .getSession(SessionManager.SERVER_NAME);
        session.write(packet);

    }
}
