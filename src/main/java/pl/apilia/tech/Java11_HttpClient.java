package pl.apilia.tech;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Java11_HttpClient {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        runHttpServer();

        runHttpClient();
    }

    private static void runHttpClient() throws IOException, InterruptedException, ExecutionException {
        //http client
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9000/hello"))
                .GET()
                .build();

        //blocking send
        HttpResponse<String> responseBlock = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(responseBlock.body());

        //async send
        CompletableFuture<HttpResponse<String>> responseAsync = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = responseAsync
                .orTimeout(5, TimeUnit.SECONDS)
                //.thenAcceptAsync()
                //.thenCombineAsync()
                .get()
                .body();
        System.out.println(responseBody);
    }

    private static void runHttpServer() throws IOException {
        //http server
        HttpHandler handler = httpExchange -> {
            String body = "hello apilia";
            httpExchange.sendResponseHeaders(200, body.length());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(body.getBytes());
            }
        };

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(9000), 0);
        httpServer.createContext("/hello", handler);
        httpServer.start();
    }
}
