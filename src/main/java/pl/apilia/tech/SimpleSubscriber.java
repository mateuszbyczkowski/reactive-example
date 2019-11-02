package pl.apilia.tech;

import static java.util.concurrent.Flow.Subscriber;
import static java.util.concurrent.Flow.Subscription;

class SimpleSubscriber<String> implements Subscriber<String> {
    private Subscription sub;

    public void onSubscribe(Subscription sub) {
        this.sub = sub;
        sub.request(1);
    }

    public void onNext(String item) {
        System.out.println("Received: " + item);
        sub.request(1);
    }

    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    public void onComplete() {
        System.out.println("Completed");
    }
}
