package view.component;

import javax.swing.*;

public class UpdatableTextArea
        extends JTextArea
        implements IUpdatable<String> {

    public UpdatableTextArea() {
        super();
    }

    @Override
    public void update(String item) {
        append(">>: " + item + "\n");
    }
}
