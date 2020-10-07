package com.alejua.example.example;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class ExampleRouter implements Handler<RoutingContext> {

	private Router router;

	public ExampleRouter(Vertx vertx, Router router, String path) {
		System.out.println("Init exampleRouter");
        this.router = router;

        router.get(path + "/saludo").handler(ctx-> {
        	System.out.println("llamo a /example/saludo");
        	ctx.response().end("hola");
        });

	}

	@Override
	public void handle(RoutingContext ctx) {
        router.handleContext(ctx);
	}

}
