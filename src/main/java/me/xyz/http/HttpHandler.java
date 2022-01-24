package me.xyz.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpHeaderValues.*;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    public HttpServer httpServer;

    public HttpHandler(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest httpRequest) {
        FullHttpResponse response;
        HttpRoute httpRoute = httpServer.getRoute(httpRequest.uri());

        if (httpRoute != null) {
            HttpContext context = new HttpContext(httpRequest.uri(), httpRequest.method(), httpRequest.headers(), httpRequest.content());
            httpRoute.route(context);
            response = new DefaultFullHttpResponse(httpRequest.protocolVersion(), OK, context.content);
        } else {
            response = new DefaultFullHttpResponse(httpRequest.protocolVersion(), NOT_FOUND,
                    Unpooled.wrappedBuffer(NOT_FOUND.toString().getBytes(StandardCharsets.UTF_8)));
        }

        response.headers()
                .set(CONTENT_TYPE, TEXT_PLAIN)
                .setInt(CONTENT_LENGTH, response.content().readableBytes());

        boolean keepAlive = HttpUtil.isKeepAlive(httpRequest);
        if (keepAlive) {
            if (!httpRequest.protocolVersion().isKeepAliveDefault()) {
                response.headers().set(CONNECTION, KEEP_ALIVE);
            }
        } else {
            response.headers().set(CONNECTION, CLOSE);
        }

        ChannelFuture channelFuture = ctx.write(response);

        if (!keepAlive) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
