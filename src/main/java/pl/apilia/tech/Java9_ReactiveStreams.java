package pl.apilia.tech;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

//Java 9+
//utility methods to support CompletableFutures
public class Java9_ReactiveStreams {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //completeTimeout();

        //throwTimeout();

        //copy();

        //reactiveStreams();
    }

    private static void completeTimeout() throws InterruptedException, ExecutionException {
        //we can complete on timeout
        CompletableFuture<String> cf = new CompletableFuture<>();

        //complete on timeout
        cf.completeOnTimeout("timed out", 5, TimeUnit.SECONDS);

        System.out.println(cf.get()); //block for 5 sec, complete

    }

    private static void throwTimeout() throws InterruptedException, ExecutionException {
        //throw on timeout
        CompletableFuture<String> cf = new CompletableFuture<>();

        cf.orTimeout(5, TimeUnit.SECONDS);

        System.out.println(cf.get()); //block for 5 sec, throw error
    }

    private static void copy() throws InterruptedException, ExecutionException {
        //defensive copy of future
        CompletableFuture<String> cf;
        CompletableFuture<String> copy;

        //complete original - example
        cf = new CompletableFuture<>();
        copy = cf.copy();
        cf.complete("done");

        System.out.println(copy.get()); //copy completed

        //complete copy - example
        cf = new CompletableFuture<>();
        copy = cf.copy();
        copy.complete("done");

        System.out.println(cf.get()); //original waiting
    }

    private static void reactiveStreams() throws InterruptedException {
        //backpressure - working with live data
        SimpleSubscriber<String> sub = new SimpleSubscriber<String>();

        SubmissionPublisher<String> pub = new SubmissionPublisher<String>();
        pub.subscribe(sub);

        pub.submit("hello guys!");
        pub.submit("nice example");

        sleep(500); //because we are closing main thread before thread with submitted tasks delivers
        pub.close();

        pub.submit("goodbye guys!");
    }
}
