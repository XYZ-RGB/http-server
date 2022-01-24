package me.xyz.http;

public class HttpServerTest {

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer(80).start();

        httpServer.route("/", ctx -> ctx.response("Hello World!"));
    }

}
