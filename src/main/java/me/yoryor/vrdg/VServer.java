package me.yoryor.vrdg;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import java.nio.file.Paths;
import java.util.Objects;

public class VServer {
  private static final Logger LOG = LoggerFactory.getLogger(VServer.class);

  private String rootDir;
  private int port = 8080;
  private String host = "localhost";
  private HttpServer server;

  public VServer() {
  }

  public VServer(String rootDir, int port, String host) {
    Objects.requireNonNull(rootDir, "必须指定静态文件夹的路径!");
    this.rootDir = rootDir;
    this.port = port;
    this.host = host;
 }

  // 初始化文件服务器
  public void initServer(Vertx vertx) {
    Router router = Router.router(vertx);
    HttpServerOptions options = new HttpServerOptions();
    options.setHost(this.host).setPort(this.port);
    server = vertx.createHttpServer(options);
    router.route("*").handler(StaticHandler.create(this.rootDir));
    server.requestHandler(router::accept).listen(ar -> {
      if (ar.succeeded()) {
        LOG.info(String.format("静态文件服务器启动成功，监听地址: %s, 监听端口: %d, 静态文件根目录: %s"),
            host, port, Paths.get(rootDir).toAbsolutePath());
      } else {
        LOG.warn("静态文件服务器启动失败", ar.cause());
      }
    });
  }
}
