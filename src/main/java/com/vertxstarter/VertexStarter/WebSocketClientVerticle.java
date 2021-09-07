package com.vertxstarter.VertexStarter;

import com.fasterxml.jackson.core.JsonToken;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;

import java.sql.PreparedStatement;
import java.util.concurrent.TimeUnit;

public class WebSocketClientVerticle extends AbstractVerticle
{
  @Override
  public void start(){
    startClient(vertx);
  }

  private void startClient(Vertx vertx){
    HttpClient client = vertx.createHttpClient();

    client.webSocket(8080, "localhost", "/", webSocketAsyncResult -> {
      webSocketAsyncResult.result().textMessageHandler((msg) ->{
        System.out.println(msg);
        webSocketAsyncResult.result().writeTextMessage("pong");
      }).exceptionHandler((e) -> {
        System.out.println("closed, restarting in 10 secs");
        restart(client,5);
      }).closeHandler((__) -> {
        System.out.println("connection closed, restarting in 10 secs");
        restart(client,10);
      });
    });
  }

  private void restart(HttpClient client, int delay)
  {
    client.close();
    vertx.setTimer(TimeUnit.SECONDS.toMillis(delay), (__) -> {
      startClient(vertx);
    });

  }



}
