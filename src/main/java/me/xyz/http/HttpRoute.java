package me.xyz.http;

@FunctionalInterface
public interface HttpRoute {
    void route(HttpContext ctx);
}
