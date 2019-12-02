package view.popupmenu;

import javax.swing.*;

public class OnlineUserListPopupMenu
        extends JPopupMenu {

    private JMenuItem addFriendMenuItem;

    public static OnlineUserListPopupMenu createMenu() {
        return new OnlineUserListPopupMenu();
    }

    private OnlineUserListPopupMenu() {
        super();
        setupMenu();
    }

    private void setupMenu() {
        addFriendMenuItem = new JMenuItem("Add friend");

        add(addFriendMenuItem);
    }

    public void setAddFriendAction(AbstractAction action) {
        addFriendMenuItem.setAction(action);
    }
}
