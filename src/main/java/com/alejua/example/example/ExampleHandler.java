package com.alejua.example.example;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class ExampleHandler implements Handler<RoutingContext> {

	private Router router;

	public ExampleHandler(Vertx vertx, Router router) {
		System.out.println("Init exampleHandler");
        this.router = router;

        router.get("/").handler(ctx-> {
        	System.out.println("llamo a /example/");
        	ctx.response().end("hola");
        });

	}

	@Override
	public void handle(RoutingContext ctx) {
        router.handleContext(ctx);
	}

}
