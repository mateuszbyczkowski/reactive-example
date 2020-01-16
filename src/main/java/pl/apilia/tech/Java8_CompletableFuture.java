package pl.apilia.tech;

import java.util.concurrent.*;

//Java 8+
//block until completed
public class Java8_CompletableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //completableFuture();

        //chainOfFutures();

        //completableJoin();

        //dealWithErrors();
    }

    private static void completableFuture() throws InterruptedException, ExecutionException {
        CompletableFuture<String> cf = new CompletableFuture<>();
        //      cf.complete("Iam done");
        //      System.out.println(cf.get());

        //       cf.completeExceptionally(new IllegalStateException());
        System.out.println(cf.get());
    }

    private static void chainOfFutures() throws ExecutionException, InterruptedException {
        //chain of completable futures
        CompletableFuture.supplyAsync(() -> "hello")
                .thenApplyAsync((x -> x + " apilia"))
                .thenAcceptAsync(System.out::println)
                .get();
    }

    private static void completableJoin() throws ExecutionException, InterruptedException {
        //completable join
        CompletableFuture.supplyAsync(() -> "hello")
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> " WMI speakers"), (x, y) -> x + y)
                .thenAcceptAsync(System.out::println)
                .get();
    }

    private static void dealWithErrors() throws ExecutionException, InterruptedException {
        //deal with errors, combining working future with failed future
        CompletableFuture.failedFuture(new IllegalStateException())
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> " apilia"), (x, y) -> x + y)
                .exceptionally(err -> "We have an error " + err.getMessage())
                .thenAcceptAsync(System.out::println);
    }
}
