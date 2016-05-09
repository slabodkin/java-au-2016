package ru.spbau.mit;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ThreadPoolTest {

    private static final int POOL_SIZE = 10;
    private static final int TASKS_NUMBER = 100;
    private static final ThreadPool THREAD_POOL = new ThreadPoolImpl(POOL_SIZE);

    @Test
    public void test1() throws LightExecutionException, InterruptedException {
        LightFuture<Integer> f = THREAD_POOL.submit(() -> 0);
        LightFuture<Integer> g = THREAD_POOL.submit(() -> 1);
        assertEquals(new Integer(0), f.get());
        assertEquals(new Integer(1), g.get());
    }

    @Test
    public void sizeTest() {
        for (int i = 0; i < TASKS_NUMBER; i++) {
            THREAD_POOL.submit(() -> 1);
        }
        assertEquals(POOL_SIZE, ((ThreadPoolImpl) THREAD_POOL).threadsSize());
    }

    @Test
    public void thenApplyTest() throws LightExecutionException, InterruptedException {
        List<LightFuture<Integer>> futureList = new ArrayList<>();

        for (int i = 0; i < TASKS_NUMBER; i++) {
            final int iCopy = i;
            LightFuture<Integer> lightFuture = THREAD_POOL.submit(() -> iCopy);
            futureList.add(lightFuture);
            lightFuture = lightFuture.thenApply(n -> n * n);
            futureList.add(lightFuture);
        }

        for (int i = 0; i < TASKS_NUMBER; i++) {
            int j = 2 * i;
            Integer r1 = futureList.get(j).get();
            Integer r2 = futureList.get(j + 1).get();
            assertEquals(r1, new Integer(i));
            assertEquals(r2, new Integer(i * i));
        }

    }

}
