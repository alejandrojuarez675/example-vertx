package com.alejua.example.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

	@Override
	public void start(Promise<Void> startPromise) throws Exception {

		logger.info("deploy verticles");
		vertx.deployVerticle(new HttpServerVerticle());
		vertx.deployVerticle(new ExampleVerticle());
		vertx.deployVerticle(new RedisVerticle());

	}
}
