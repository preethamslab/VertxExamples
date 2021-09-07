package com.vertxstarter.VertexStarter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

import java.util.Random;

public class WebsocketServerVerticle extends AbstractVerticle {

    @Override
    public void start(){
      startServer(vertx);
    }

    private void startServer(Vertx vertx){
      HttpServer server = vertx.createHttpServer();
      server.webSocketHandler((ctx) -> {
        ctx.writeTextMessage("ping");

        ctx.textMessageHandler((msg) -> {
          System.out.println("serve "+msg);

          if ((new Random().nextInt(100) == 0)) {
            ctx.close();
          }else
          {
            ctx.writeTextMessage("ping");
          }
        });

      }).listen(8080);

    }


}
