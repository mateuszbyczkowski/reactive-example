package pl.apilia.tech;

//Java 1+
public class Java1_ThreadAndRunnable {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println("hello apilia tech"));
        thread.start();
    }
}
