# http-server
```
    public static void main(String[] args) {
        WebServer httpServer = new WebServer(80).start();

        httpServer.route("/", ctx -> {
            ctx.response("Hello World!");
        });
    }
```