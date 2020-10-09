package com.alejua.example.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class ExampleVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(ExampleVerticle.class);

	public static final String ADD_SALUDO = "example.saludo.address";
	
	@Override
	public void start() throws Exception {
		logger.info("Init ExampleVerticle");
		super.start();
		
		vertx.eventBus().consumer(ADD_SALUDO, this::eventTargetSaludo);
	}

	private void eventTargetSaludo(Message<String> msg) {
		logger.info("Event " + ADD_SALUDO);
		msg.reply("Hola desde ExampleVerticle");
	}
}
