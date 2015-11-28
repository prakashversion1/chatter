package com.pcjoshi.chatter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class ChattyServer extends AbstractVerticle {

    @Override
    public void start(Future<Void> fut) {
        Router router = Router.router(vertx);
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/html")
                    .end("<html><h1>Hello world<h1></html>");
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8080,result -> {
            if (result.succeeded()){
                fut.complete();
                System.out.println("Successfull deployed server at port 8080");
            }else {
                fut.fail(result.cause());
            }
        });

    }
}
