# http-server
## Usage

```java
package me.xyz.http;

public class HttpServerTest {

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer(80).start();

        httpServer.route("/", ctx -> ctx.response("Hello World!"));
    }

}
```
https://github.com/XYZ-RGB/http-server/blob/master/src/test/java/me/xyz/HttpServerTest.java

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)