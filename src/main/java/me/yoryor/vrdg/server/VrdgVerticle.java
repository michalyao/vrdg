package me.yoryor.vrdg.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import java.util.Objects;

import static me.yoryor.vrdg.Util.getAbsolutePath;

public class VrdgVerticle extends AbstractVerticle{
  private static final Logger LOG = LoggerFactory.getLogger(VrdgVerticle.class);

  private String rootDir;
  private int port;
  private String host;
  private boolean devModeEnabled;

  public VrdgVerticle() {
  }

  public VrdgVerticle(String rootDir, int port, String host, boolean devModeEnabled) {
    Objects.requireNonNull(rootDir, "必须指定静态文件夹的路径!");
    this.rootDir = rootDir;
    this.port = port;
    this.host = host;
    this.devModeEnabled = devModeEnabled;
 }

  @Override
  public void start() throws Exception {
    initServer(vertx);
  }

  // 初始化文件服务器
  private void initServer(Vertx vertx) {
    Router router = Router.router(vertx);
    HttpServerOptions options = new HttpServerOptions();
    options.setHost(this.host).setPort(this.port);
    HttpServer server = vertx.createHttpServer(options);
    router.route("/*").handler(StaticHandler.create(this.rootDir).setCachingEnabled(devModeEnabled));
    server.requestHandler(router::accept).listen(ar -> {
      if (ar.succeeded()) {
        LOG.info(String.format("静态文件服务器启动成功，监听地址: %s, 监听端口: %d, 静态文件根目录: %s",
            host, port, getAbsolutePath(rootDir)));
      } else {
        LOG.warn("静态文件服务器启动失败", ar.cause());
      }
    });
  }
}
