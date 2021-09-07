package com.vertxstarter.VertexStarter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

//class to understand the vert.x toolkit
public class MainVerticle extends AbstractVerticle {
/*  //overriding the abstract method of the AbstractVerticle class
  @Override
  public void start() throws Exception {
    // instantiate the object of the Router class to create router
    Router router = Router.router(vertx);

    //At every path and HTTP method for all incoming requests mount the handler
    router.route().handler(context -> {
      // Find the address of the request by using request(), connection() and remoteAddress() method
      String address = context.request().connection().remoteAddress().toString();

      // Find the "name" query parameter from the queryParams() by using get() method
      MultiMap queryParams = context.queryParams();
      String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
      // write information in JSON format as a response
      context.json(
        new JsonObject()
          .put("name", name)
          .put("address", address)
          .put("message", "Hello " + name + " connected from " + address)
      );
    });

    // use createHttpServer() method to create Http server
    vertx.createHttpServer()
      // use router to handle each upcoming request
      .requestHandler(router)
      // use listen() method to start listening
      .listen(8888)
      // using actualPort() method of server to print port
      .onSuccess(server ->
        System.out.println(
          "HTTP server started on port " + server.actualPort()
        )
      );
  }*/

  public static void main(String[] args) throws InterruptedException {
/*    System.out.println(Thread.activeCount());
    Vertx vertx = Vertx.vertx();
    System.out.println(Thread.activeCount());

    vertx.deployVerticle(new WebsocketServerVerticle(), (__) ->{
        vertx.deployVerticle(new WebSocketClientVerticle());
    });*/
    Vertx vertx = Vertx.vertx();

    final Map<String, AtomicInteger> threadCounts = new ConcurrentHashMap<>();

    int verticles = 1000;
    final CountDownLatch latch = new CountDownLatch(verticles);
    for (int i = 0; i < verticles; i++) {
      //vertx.deployVerticle(new MyVerticle(threadCounts), c -> latch.countDown());
      vertx.deployVerticle(new DummyVertical(threadCounts), c -> latch.countDown());
    }
    latch.await();

    System.out.println(Thread.activeCount());

    System.out.println(threadCounts);

   /* for (String name:threadCounts.keySet()
         ) {
      System.out.println(threadCounts);
    }*/
  }
}
