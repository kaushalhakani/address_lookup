/**
 * 
 */
package com.workmarket.test.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kushh
 *
 */
public class HttpRequestUtils {
  private static Logger logger = Logger.getLogger(HttpRequestUtils.class.getName());

  public static String httpGet(String baseUrl, String address) throws IOException {
    URL url = new URL(String.format("%s?address=%s", baseUrl, URLEncoder.encode(address, "UTF-8")));
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.connect();

    String message = null;
    if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
      message = getResponseMessage(connection);
    } else {
      String error = getResponseError(connection);
      logger.logp(Level.WARNING, HttpRequestUtils.class.getName(), "httpGet", error);
    }
    connection.disconnect();
    return message;
  }

  private static String getResponseMessage(HttpURLConnection connection) {
    StringBuilder sb = new StringBuilder();

    try (InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
        BufferedReader br = new BufferedReader(inputStreamReader)) {
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line + "\n");
      }
    } catch (IOException e) {
      logger.logp(Level.WARNING, HttpRequestUtils.class.getName(), "getResponseMessage", "Error Occurred: ", e);
    }

    return sb.toString();
  }

  private static String getResponseError(HttpURLConnection connection) {
    StringBuilder sb = new StringBuilder();

    try (InputStreamReader inputStreamReader = new InputStreamReader(connection.getErrorStream());
        BufferedReader br = new BufferedReader(inputStreamReader)) {
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line + "\n");
      }
    } catch (IOException e) {
      logger.logp(Level.WARNING, HttpRequestUtils.class.getName(), "getResponseError", "Error Occurred: ", e);
    }

    return sb.toString();
  }
}
