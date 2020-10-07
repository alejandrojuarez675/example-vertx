package com.alejua.example.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {

		System.out.println("deploy verticles");
		vertx.deployVerticle(new HttpServerVerticle());
		vertx.deployVerticle(new ExampleVerticle());

	}
}
