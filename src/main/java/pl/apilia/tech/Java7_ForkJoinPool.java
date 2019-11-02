package pl.apilia.tech;

import java.util.concurrent.*;

//Java 7+
//benefits of futures and load balancing tasks on threads (increase performance)
public class Java7_ForkJoinPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = ForkJoinPool.commonPool();

        Future<String> future = executorService.submit(() -> "hello apilia");

        String result = future.get(); //blocking call

        System.out.println(result);

        if (future.isDone()) {
            System.out.println(future.get()); //busy waiting
        }
    }
}
