package view.popupmenu;

import javax.swing.*;

public class FriendListPopupMenu
        extends JPopupMenu {

    private JMenuItem deleteFriendMenuItem;

    public static FriendListPopupMenu createMenu() {
        return new FriendListPopupMenu();
    }

    private FriendListPopupMenu() {
        super();
        setupMenu();
    }

    private void setupMenu() {
        deleteFriendMenuItem = new JMenuItem("Delete friend");
        add(deleteFriendMenuItem);
    }

    public void setDeleteFriendAction(AbstractAction action) {
        deleteFriendMenuItem.setAction(action);
    }

}
