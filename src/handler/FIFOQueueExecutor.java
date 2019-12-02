package handler;

import queue.BundleQueue;
import queue.IExecutor;
import util.SystemLogger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FIFOQueueExecutor
        implements IExecutor {

    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            2,
            10,
            500,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(10),
            new ThreadPoolExecutor.CallerRunsPolicy());

    private ResponseHandler responseHandler;

    public FIFOQueueExecutor(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        execute();
    }

    @Override
    public void execute() {
        SystemLogger.info("Starting executor...");
        while (!Thread.interrupted()) {
            if (!BundleQueue.isEmpty()) {
                var bundle = BundleQueue.poll();
                assert bundle != null;
                threadPool.execute(new Process(bundle, responseHandler));
            }
        }
    }
}
