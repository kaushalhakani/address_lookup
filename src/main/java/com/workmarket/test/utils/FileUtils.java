/**
 * 
 */
package com.workmarket.test.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kushh
 *
 */
public class FileUtils {
  private static Logger logger = Logger.getLogger(FileUtils.class.getName());

  // Get file from resources folder
  private static ClassLoader classLoader = FileUtils.class.getClassLoader();

  public static List<String> readAllLines(String fileName) {
    List<String> result = new LinkedList<>();
    URL fileURL = classLoader.getResource(fileName);
    if (fileURL == null) {
      logger.logp(Level.WARNING, FileUtils.class.getSimpleName(), "readAllLines", "Unable to find file", fileName);
      return null;
    }

    File file = new File(fileURL.getFile());
    FileReader fr = null;
    try {
      fr = new FileReader(file);
    } catch (FileNotFoundException e) {
      logger.logp(Level.SEVERE, FileUtils.class.getSimpleName(), "readAllLines", "Unable to find file", e);
      return null;
    }
    String line;
    try (BufferedReader bufr = new BufferedReader(fr)) {
      while ((line = bufr.readLine()) != null) {
        result.add(line);
      }
      bufr.close();
    } catch (IOException e) {
      logger.logp(Level.SEVERE, FileUtils.class.getSimpleName(), "readAllLines", "Error Occurred: ", e);
      return null;
    }

    return result;
  }
}
