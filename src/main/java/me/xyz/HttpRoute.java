package me.xyz;

@FunctionalInterface
public interface HttpRoute {
    void route(HttpContext ctx);
}
