package me.xyz.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.AsciiString;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderValues.TEXT_PLAIN;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

public class HttpContext {
    public final String uri;
    public final HttpMethod method;
    public final HttpHeaders headers;
    public final ByteBuf data;
    public ByteBuf content;
    public AsciiString contentType;
    public HttpResponseStatus responseStatus;

    public HttpContext(String uri, HttpMethod method, HttpHeaders headers, ByteBuf data) {
        this.uri = uri;
        this.method = method;
        this.headers = headers;
        this.data = data;
    }

    public void response(String response) {
        this.response(response, TEXT_PLAIN);
    }

    public void response(String response, AsciiString contentType) {
        response(response, contentType, OK);
    }

    public void response(String response, AsciiString contentType, HttpResponseStatus responseStatus) {
        response(Unpooled.copiedBuffer(response, StandardCharsets.UTF_8), contentType, responseStatus);
    }

    public void response(ByteBuf response, AsciiString contentType, HttpResponseStatus responseStatus) {
        this.content = response;
        this.contentType = contentType;
        this.responseStatus = responseStatus;
    }

    public ByteBuf getContent() {
        return content;
    }

    public void setContent(ByteBuf content) {
        this.content = content;
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public AsciiString getContentType() {
        return contentType;
    }

    public void setContentType(AsciiString contentType) {
        this.contentType = contentType;
    }

    public HttpResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(HttpResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getData() {
        return data.toString(StandardCharsets.UTF_8);
    }

    public ByteBuf getDataByteBuf() {
        return data;
    }
}
