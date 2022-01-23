package me.xyz;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;

import java.util.HashMap;

public class HttpServer {
    private final HashMap<String, HttpRoute> routes = new HashMap<>();
    private final int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public HttpServer start() {
        try {
            new ServerBootstrap()
                    .group(new NioEventLoopGroup(1), new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new HttpServerExpectContinueHandler());
                            pipeline.addLast(new HttpHandler(HttpServer.this));
                        }
                    }).bind(port).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public void route(String path, HttpRoute route) {
        routes.put(path, route);
    }

    public HttpRoute getRoute(String path) {
        return routes.get(path);
    }
}
