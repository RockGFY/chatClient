package view.component;

import javax.swing.*;
import java.awt.*;

public class UpdatableJList
        extends JList<String>
        implements IUpdatable<String> {

    @Override
    public void update(String senderName) {
        setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (senderName.equals(value)) {
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    c.setFont(c.getFont());
                }
                return c;
            }
        });
    }

    public void unBold(String senderName) {
        setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (senderName.equals(value)) {
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                } else {
                    c.setFont(c.getFont());
                }
                return c;
            }
        });
    }
}
