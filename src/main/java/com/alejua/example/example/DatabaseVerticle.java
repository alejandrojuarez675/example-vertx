package com.alejua.example.example;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;

public class DatabaseVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseVerticle.class);

	public static final String ADDR_GET_USERS = "database.get.users.address";


	@Override
	public void start() throws Exception {
		logger.info("Init DatabaseVerticle");
		super.start();

		PgConnectOptions connectOptions = new PgConnectOptions()
				.setPort(5432)
				.setHost("localhost")
				.setDatabase("examplevertx")
				.setUser("postgres")
				.setPassword("postgres");

		PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

		vertx.eventBus().consumer(ADDR_GET_USERS, msg -> eventTargetGetUsers(msg, connectOptions, poolOptions));

	}

	private void eventTargetGetUsers(Message<Object> msg, PgConnectOptions connectOptions, PoolOptions poolOptions) {

		PgPool client = PgPool.pool(vertx, connectOptions, poolOptions);
		client.query("SELECT * FROM users").execute(ar -> {
			if (ar.succeeded()) {
				logger.info("res desde la db");
				
				Spliterator<Row> spliteratorUnknownSize = Spliterators.spliteratorUnknownSize(
						ar.result().iterator(), Spliterator.ORDERED);
				
				List<String> res = StreamSupport.stream(spliteratorUnknownSize, false)
						.map(x->x.getString(1))
						.collect(Collectors.toList());
				
				JsonObject object = new JsonObject().put("usuarios", res);
				
				msg.reply(object);
			} else {
				logger.error(ar.cause());
				msg.fail(0, ar.cause().getMessage());
			}

			client.close();
		});
	}
}
