/**
 * 
 */
package com.workmarket.test.base.location;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.workmarket.test.base.address.AddressDetail;
import com.workmarket.test.utils.HttpRequestUtils;

/**
 * @author kushh
 *
 */
public class GoogleAPILocationExtractor implements ILocationExtractor {

  private static final String URL = "https://maps.googleapis.com/maps/api/geocode/json";
  private static Logger logger = Logger.getLogger(GoogleAPILocationExtractor.class.getName());

  @Override
  public Location extractLocation(AddressDetail addressDetails) {
    try {
      String jsonString = HttpRequestUtils.httpGet(URL, addressDetails.getAddress());
      return processResponse(jsonString);
    } catch (IOException e) {
      logger.logp(Level.WARNING, this.getClass().getName(), "extractLocation", "Error Occurred: ", e);
    }
    return null;
  }

  private Location processResponse(String jsonString) {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(jsonString);

    if (jsonTree.isJsonObject()) {
      JsonObject jsonObject = jsonTree.getAsJsonObject();
      JsonArray results = jsonObject.getAsJsonArray("results");

      if (results != null && results.size() > 0) {
        // Assumption that first element in the result is the most appropriate one!
        JsonObject result = results.get(0).getAsJsonObject();
        JsonObject resultGeometry = result.getAsJsonObject("geometry");
        JsonObject resultLocation = resultGeometry.getAsJsonObject("location");
        
        if(resultLocation != null) {
          Double longitude = resultLocation.get("lng").getAsDouble();
          Double latitude = resultLocation.get("lat").getAsDouble();
          return new Location(latitude, longitude);
        }
      }
    }
    return null;
  }
}
