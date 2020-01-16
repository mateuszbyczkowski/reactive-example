package pl.apilia.tech;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class RxJava {
    public static void main(String[] args) throws Exception {
        //simpleObserver();

        //questionObs();

        //zipExample();

        //moreFeatures();

        //periodObservable();

        boxingSlowService();

    }

    private static void simpleObserver() {
        Observable<String> ob = Observable.just("hello", "world", "apilia", "apilia");

        // simple observer
        ob.subscribe(new Observer<String>() {
            @Override
            public void onComplete() {
                System.out.println("-----completed-----");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("error");
            }

            @Override
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });

        // java8+ style, still simple observer
        ob.subscribe(
                System.out::println,
                throwable -> System.out.println("error"),
                () -> System.out.println("-----completed-----")
        );
    }

    private static void questionObs() {
        Observable<String> ob = Observable.just("hello", "world", "apilia", "apilia");

        // advanced observer
        // what is the result of the following stream?
        Observable<String> chainObservable = ob
                .doOnComplete(() -> System.out.println("Clean up this mess"))
                .filter(x -> x.equals("apilia"))//apilia apilia
                .distinct()//apilia
                .map(x -> "This is " + x + "!")
                .map(String::toUpperCase)
                .doOnError(t -> System.out.println("Error: " + t.getMessage()));

        chainObservable
                .subscribe(System.out::println);
    }

    private static void zipExample() {
        // advanced example
        Observable<String> obs1 = Observable.just("hello", "apilia", "pozdrawiam")
                .cache()
                .repeat(3)
                .subscribeOn(Schedulers.from(Executors.newFixedThreadPool(3)));

        Observable<String> obs2 = Observable.just("world", "tech", "serdecznie")
                .repeat(3)
                .subscribeOn(Schedulers.from(Executors.newFixedThreadPool(3)));

        final Observable<String> zip = obs1.zipWith(obs2, (o1, o2) -> o1 + " " + o2)
                .doOnComplete(() -> System.out.println("----------"));

        zip.subscribe(System.out::println);
    }

    private static void moreFeatures() throws InterruptedException {

        Observable<String> ob = Observable.just("hello", "world", "apilia", "apilia");

        Observable<String> obs = ob
                .delay(3, TimeUnit.SECONDS)
                .timeout(5, TimeUnit.SECONDS)
                .doOnError((throwable -> System.out.println(throwable.getMessage())))
                .subscribeOn(Schedulers.newThread());

        obs.subscribe(System.out::println);


        sleep(20000);
    }

    private static void periodObservable() throws InterruptedException {
        Observable
                .interval(1, TimeUnit.SECONDS)
                .map(x -> Math.pow(x, 2))
                .map(Math::round)
                .subscribe(System.out::println);

        sleep(20000);
    }

    private static void boxingSlowService() {
        Observable<BigDecimal> response = verySlowAndFaultyService()
                .timeout(1, TimeUnit.SECONDS, Schedulers.newThread())
                .doOnError(System.out::println)
                .retry(4)
                .onErrorReturn(x -> BigDecimal.ONE.negate());

        response
                .blockingSubscribe(System.out::println);
    }

    private static Observable<BigDecimal> verySlowAndFaultyService() {
        return Observable
                .timer(1, TimeUnit.MINUTES)
                .map(x -> BigDecimal.ZERO);
    }
}
