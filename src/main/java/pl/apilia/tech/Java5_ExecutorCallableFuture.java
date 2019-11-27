package pl.apilia.tech;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//Java 5+
//ExecutorService, Callable/Runnable and Futures
public class Java5_ExecutorCallableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<String> future = executorService.submit(() -> "hello apilia");

        String result = future.get(); //blocking call

        System.out.println(result);

        if (future.isDone()) {
            System.out.println(future.get()); //busy waiting
        }
    }
}
