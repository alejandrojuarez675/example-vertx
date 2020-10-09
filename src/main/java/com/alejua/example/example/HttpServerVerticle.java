package com.alejua.example.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

public class HttpServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		logger.info("Init HttpServerVerticle");
		super.start();

		logger.info("creo httpServer");
		HttpServer server = vertx.createHttpServer();

		logger.info("init rutas");
		Router router = Router.router(vertx);
		router.route("/example").handler(new ExampleRouter(vertx, router, "/example"));
		router.route().handler(ErrorHandler::notFountError);
		
		server.requestHandler(router).listen(8888, http -> {
			if (http.succeeded()) {
				startPromise.complete();
				logger.info("HTTP server started on port 8888");
			} else {
				startPromise.fail(http.cause());
			}
		});

	}

}
