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

		router.get(path + "/saludo").handler(ctx -> getSaludo(ctx, vertx));

	}

	private void getSaludo(RoutingContext ctx, Vertx vertx) {
		System.out.println("llamo a /example/saludo");

		vertx.eventBus().request(ExampleVerticle.ADD_SALUDO, null, reply -> {
			if (reply.succeeded()) {
				String saludo = (String) reply.result().body();
				ctx.response().end(saludo);
			} else {
				ctx.response().setStatusCode(500).setStatusMessage("Internal error");
			}
		});
	}

	@Override
	public void handle(RoutingContext ctx) {
		router.handleContext(ctx);
	}

}
