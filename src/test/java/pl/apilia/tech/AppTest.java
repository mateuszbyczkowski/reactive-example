package pl.apilia.tech;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void whenSubscribeToIt_thenShouldConsumeAll() throws InterruptedException {

        // given
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        SimpleSubscriber<String> subscriber = new SimpleSubscriber<>();
        publisher.subscribe(subscriber);
        List<String> items = List.of("1", "x", "2", "x", "3", "x");

        // when
        for (String item : items) {
            publisher.submit(item);
        }

        publisher.close();
    }
}
