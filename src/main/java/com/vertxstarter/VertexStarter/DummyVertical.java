package com.vertxstarter.VertexStarter;

import io.vertx.core.AbstractVerticle;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DummyVertical extends AbstractVerticle {
  private final Map<String, AtomicInteger> threadCounts;

  DummyVertical(Map<String, AtomicInteger> count)
  {
    this.threadCounts = count;
  }
  @Override
  public void start() {
    threadCounts.computeIfAbsent(Thread.currentThread().getName(),
      t -> new AtomicInteger(0)).incrementAndGet();
  }

}
