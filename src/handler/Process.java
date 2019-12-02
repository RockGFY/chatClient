package handler;

import queue.Bundle;

public class Process
        implements Runnable {

    private Bundle bundle;
    private ResponseHandler handler;

    Process(Bundle bundle, ResponseHandler handler) {
        this.bundle = bundle;
        this.handler = handler;
    }

    @Override
    public void run() {

        try {
            handler.handle(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
