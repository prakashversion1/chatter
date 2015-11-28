package com.pcjoshi.chatter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class ChattyServer extends AbstractVerticle {

    @Override
    public void start(Future<Void> fut) {
        // Demo html for now
        String html = "<link rel=\"stylesheet\" href=\"https://goo.gl/fUS2lx\">'\n" +
                "    + '<h1 style=\"color:navy;position:relative;top:50%;transform:translateY(-50%);\"'\n" +
                "    + 'align=\"center\" class=\"animated infinite rubberBand\">Hello Chatty</h1>";



        Router router = Router.router(vertx);
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/html")
                    .end(html);
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8080, result -> {
            if (result.succeeded()) {
                fut.complete();
                System.out.println("Successfull deployed server at port 8080");
            } else {
                fut.fail(result.cause());
            }
        });

    }
}
