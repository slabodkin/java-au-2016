package ru.spbau.mit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Supplier;

public class ThreadPoolImpl implements ThreadPool {

    private boolean shutdowned;
    private Queue<LightFutureImpl> futureQueue;
    private List<Thread> threads;


    public ThreadPoolImpl(int n) {
        shutdowned = false;
        futureQueue = new LinkedList<>();
        threads = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Thread thread = new Thread(new FutureWorker());
            thread.start();
            threads.add(thread);
        }
    }




    public <R> LightFutureImpl<R> addTask(LightFutureImpl<R> task) {
        synchronized (futureQueue) {
            futureQueue.add(task);
            futureQueue.notify();
        }
        return task;
    }

    @Override
    public <R> LightFuture<R> submit(Supplier<R> supplier) {
        LightFutureImpl<R> newTask = new LightFutureImpl<R>(this) {
            @Override
            protected R compute() {
                return supplier.get();
            }
        };
        return addTask(newTask);
    }

    @Override
    public synchronized void shutdown() {
        shutdowned = true;
        for (Thread thread : threads) {
            thread.interrupt();
        }
        threads.clear();
    }

    public int threadsSize() {
        return threads.size();
    }

    Queue<LightFutureImpl> getFutureQueue() {
        return futureQueue;
    }

    private class FutureWorker implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    LightFutureImpl f;
                    synchronized (futureQueue) {
                        while (futureQueue.isEmpty()) {
                            futureQueue.wait();
                        }
                        f = futureQueue.poll();
                        futureQueue.notify();
                    }
                    f.getRes();
                }
            } catch (InterruptedException ignored) { }
        }
    }

}
