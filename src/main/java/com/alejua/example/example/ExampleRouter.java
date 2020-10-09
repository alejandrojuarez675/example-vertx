package com.alejua.example.example;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class ExampleRouter implements Handler<RoutingContext> {

	private static final Logger logger = LoggerFactory.getLogger(ExampleRouter.class);

	private Router router;

	public ExampleRouter(Vertx vertx, Router router, String path) {
		logger.info("Init exampleRouter");
		this.router = router;

		router.get(path + "/saludo").handler(ctx -> getSaludo(ctx, vertx));
		router.get(path + "/objetos").handler(ctx -> getObjetos(ctx, vertx));

	}

	private void getSaludo(RoutingContext ctx, Vertx vertx) {
		logger.info("llamo a /example/saludo");

		vertx.eventBus().request(ExampleVerticle.ADD_SALUDO, null, reply -> {
			if (reply.succeeded()) {
				String saludo = (String) reply.result().body();
				ctx.response().end(saludo);
			} else {
				ctx.response().setStatusCode(500).setStatusMessage("Internal error");
			}
		});
	}
	
	private void getObjetos(RoutingContext ctx, Vertx vertx) {
		logger.info("Llamo a /example/objetos");

		logger.info("Request Event " + RedisVerticle.ADDR_OBJETOS);
		vertx.eventBus().request(RedisVerticle.ADDR_OBJETOS, "", reply -> {
			if (reply.succeeded()) {

				ctx.response()
					.putHeader("content-type", "application/json")
					.end(reply.result().body().toString());
				
			} else {
				logger.error("ERROR: " + reply.cause().getMessage());
				ctx.response().setStatusCode(500).setStatusMessage("Internal error").end();
			}
		});
	}

	@Override
	public void handle(RoutingContext ctx) {
		router.handleContext(ctx);
	}

}
