package me.yoryor.vrdg;

import java.nio.file.Files;
import java.nio.file.Paths;

public final class Util {
  private Util() {}

  public static String getAbsolutePath(String path) {
    return Paths.get(path).toAbsolutePath().normalize().toString();
  }

  public static boolean fileExists(String path) {
    return Files.exists(Paths.get(path));
  }

}
