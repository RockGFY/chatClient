package view.component;

import communication.ConnectionStatus;

import javax.swing.*;
import java.util.concurrent.Flow;

public class SubscriberButton
    extends JButton
    implements Flow.Subscriber<ConnectionStatus> {

    private Flow.Subscription subscription;

    public SubscriberButton() {
        super();
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(ConnectionStatus item) {
        if (item == ConnectionStatus.CONNECTED) {
            firePropertyChange("connected", true, false);
        }
        else {
            firePropertyChange("disconnected", true, false);
        }
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
