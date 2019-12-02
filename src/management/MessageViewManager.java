package management;

import com.google.common.collect.HashBiMap;
import view.MessageView;

public class MessageViewManager {

    private static HashBiMap<String, MessageView> messageViewMap;

    private static MessageViewManager instance = null;

    public static MessageViewManager getInstance() {
        if (instance == null) {
            synchronized (MessageViewManager.class) {
                if (instance == null)
                    instance = new MessageViewManager();
            }
        }
        return instance;
    }

    private MessageViewManager() {
        messageViewMap = HashBiMap.create();
    }

    public void add(String key, MessageView view) {
        messageViewMap.put(key, view);
    }

    public MessageView getMessageView(String key) {
        return messageViewMap.get(key);
    }

    public void removeMessageView(MessageView messageView) {
        messageViewMap.inverse().remove(messageView);
    }

}
