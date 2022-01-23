package me.xyz;

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
    public ByteBuf content;
    public String uri;
    public HttpMethod method;
    public HttpHeaders headers;
    public AsciiString contentType = TEXT_PLAIN;
    public HttpResponseStatus responseStatus = OK;

    public HttpContext(String uri, HttpMethod method, HttpHeaders headers) {
        this.uri = uri;
        this.method = method;
        this.headers = headers;
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

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
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
}
