package com.alejua.example.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class HttpServerVerticle extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		super.start();

		System.out.println("creo httpServer");
		HttpServer server = vertx.createHttpServer();

		System.out.println("init rutas");
		Router router = Router.router(vertx);
		router.route("/example").handler(new ExampleRouter(vertx, router, "/example"));
		router.route().handler(ErrorHandler::notFountError);
		
		server.requestHandler(router).listen(8888, http -> {
			if (http.succeeded()) {
				startPromise.complete();
				System.out.println("HTTP server started on port 8888");
			} else {
				startPromise.fail(http.cause());
			}
		});

	}

}
