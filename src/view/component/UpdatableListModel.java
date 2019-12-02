package view.component;

import protocol.entity.IEntity;
import protocol.entity.UserIP;

import javax.swing.*;

public class UpdatableListModel
        extends DefaultListModel<String>
        implements IUpdatable<IEntity> {

    public UpdatableListModel() {
        super();
    }

    @Override
    public void update(IEntity entity) {

        if (entity instanceof UserIP) {
            var userIP = (UserIP) entity;
            addElement(userIP.getUserName());
        }
    }

}
