package com.alejua.example.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;

public class ExampleVerticle extends AbstractVerticle {

	public static final String ADD_SALUDO = "example.saludo.address";
	
	@Override
	public void start() throws Exception {
		System.out.println("Init ExampleVerticle");
		super.start();
		
		vertx.eventBus().consumer(ADD_SALUDO, this::eventTargetSaludo);
	}

	private void eventTargetSaludo(Message<String> msg) {
		System.out.println("Event " + ADD_SALUDO);
		msg.reply("Hola desde ExampleVerticle");
	}
}
