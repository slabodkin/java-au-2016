package ru.spbau.mit;

import java.util.function.Function;


public class LightFutureImpl<R> implements LightFuture<R> {
    private LightFutureImpl<?> dependent;
    private volatile boolean isReady;
    private R res;
    private LightExecutionException leException;
    private final ThreadPoolImpl threadPool;


    public LightFutureImpl(ThreadPoolImpl t) {
        threadPool = t;
        dependent = null;
    }

    @Override
    public boolean isReady() {
        return isReady;
    }

    @Override
    public R get() throws LightExecutionException, InterruptedException {
        if (!isReady()) {
            synchronized (this) {
                while (!isReady()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        if (leException != null) {
            throw leException;
        }
        getRes();
        return res;
    }

    public void getRes() {
        if (dependent != null) {
            dependent.getRes();
        }
        try {
            res = compute();
        } catch (Exception e) {
            leException = new LightExecutionException(e);
        }

        synchronized (this) {
            isReady = true;
            notifyAll();
        }
    }

    public void setDependent(LightFutureImpl lf) {
        dependent = lf;
    }

    @Override
    public <U> LightFuture<U> thenApply(Function<? super R, ? extends U> f) {
        LightFutureImpl<R> temp = this;
        LightFutureImpl<U> newTask = new LightFutureImpl<U>(threadPool) {
            @Override
            protected U compute() throws LightExecutionException, InterruptedException {
                return f.apply(temp.get());
            }
        };
        newTask.setDependent(this);
        synchronized (threadPool.getFutureQueue()) {
            threadPool.getFutureQueue().add(newTask);
            threadPool.getFutureQueue().notify();
        }
        return newTask;
    }

    protected R compute() throws LightExecutionException, InterruptedException {
        return res;
    }

}

