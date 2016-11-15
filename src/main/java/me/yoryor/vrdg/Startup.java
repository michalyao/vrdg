package me.yoryor.vrdg;

import io.vertx.core.Vertx;
import io.vertx.core.cli.CLI;
import io.vertx.core.cli.CommandLine;
import io.vertx.core.cli.Option;
import io.vertx.core.cli.TypedOption;
import me.yoryor.vrdg.server.VrdgVerticle;

import java.util.Arrays;

import static me.yoryor.vrdg.Util.*;

public class Startup {
  private static CLI cli = cli();

  public static void main(String[] args) {
    CommandLine commandLine = cli.parse(Arrays.asList(args), false);
    if (!commandLine.isValid()) {
      System.out.println(usage());
      System.exit(1);
    }
    String host = commandLine.getOptionValue("host");
    int port = commandLine.getOptionValue("port");
    String root = commandLine.getOptionValue("root");
    boolean devModeEnabled = commandLine.getOptionValue("dev") != null;
    if (!fileExists(root)) {
      System.err.printf("输入的静态文件根目录: %s 不存在，请检查后重新启动%n", getAbsolutePath(root));
      System.exit(1);
    }
    VrdgVerticle vrdgVerticle = new VrdgVerticle(root, port, host, devModeEnabled);
    Vertx.vertx().deployVerticle(vrdgVerticle);
  }

  private static String usage() {
    StringBuilder sb = new StringBuilder();
    cli.usage(sb);
    return sb.toString();
  }

  private static CLI cli() {
    return CLI.create("vrdg")
        .setSummary("A simple yet powerful static file server.")
        .addOption(new Option()
            .setLongName("host")
            .setShortName("h")
            .setDescription("Server adderss")
            .setDefaultValue("localhost"))
        .addOption(new TypedOption<Integer>()
            .setType(Integer.class)
            .setLongName("port")
            .setShortName("p")
            .setDescription("Server port.")
            .setDefaultValue("80"))
        .addOption(new Option()
            .setLongName("root")
            .setShortName("r")
            .setRequired(true)
            .setDescription("Root directory of the static files."))
        .addOption(new Option()
            .setFlag(true)
            .setLongName("dev")
            .setShortName("d")
            .setDescription("Dev mode"));
  }
}
