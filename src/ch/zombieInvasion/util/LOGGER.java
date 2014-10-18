package ch.zombieInvasion.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LOGGER {
  public static void LOG(String txt) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("DebugLog.txt", true));
      if (txt.equals("\n")) {
        writer.write("\n");
      } else {
        writer.write(LocalDateTime.now() + "  " + txt + "\n");
      }
      writer.close();
    } catch (IOException e) {
      System.out.println("ERROR in LOGGER");
    }
  };

  static {
    LOG("\n");
  }

}
