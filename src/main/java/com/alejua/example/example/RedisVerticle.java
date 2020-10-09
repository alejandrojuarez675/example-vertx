package com.alejua.example.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisConnection;

public class RedisVerticle extends AbstractVerticle {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisVerticle.class);
	
	public static final String ADDR_OBJETOS = "redis.objetos.address";

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		logger.info("Init RedisVerticle");

		super.start();

		Redis.createClient(vertx, "redis://localhost:6379").connect(onConnect -> {
			if (onConnect.succeeded()) {
				RedisConnection client = onConnect.result();
				RedisAPI redis = RedisAPI.api(client);

				vertx.eventBus().consumer(ADDR_OBJETOS, msg -> eventTargetSaludo(msg, redis));
				
			} else {
				vertx.close();
			}
		});
	}
	
	private void eventTargetSaludo(Message<Object> msg, RedisAPI redis) {
		logger.info("Event " + ADDR_OBJETOS);

		redis.get("objetos", res -> {
			if (res.succeeded()) {
				String lectura = res.result().toString();
				msg.reply(lectura);
			} else {
				logger.error(res.cause());
				msg.fail(0, res.cause().getMessage());
			}
		});
	}


}
