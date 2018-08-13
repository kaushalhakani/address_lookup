/**
 * 
 */
package com.workmarket.test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.workmarket.test.base.address.AddressDetail;
import com.workmarket.test.base.location.GoogleAPILocationExtractor;
import com.workmarket.test.base.location.LocationStatus;
import com.workmarket.test.utils.FileUtils;

/**
 * @author kushh
 *
 */
public class MainApp {

  private static final int MAX_ATTEMPTS = 5;
  private static Logger logger = Logger.getLogger(MainApp.class.getName());
  private static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

  public static void main(String args[]) {
    final String fileName = "addresses.txt";

    // Step 1: read lines from file
    List<String> addresses = FileUtils.readAllLines(fileName);

    if (addresses == null)
      return;

    // Step 2: create address objects and extract location
    List<AddressDetail> addressDetailList = new ArrayList<AddressDetail>(addresses.size());
    for (String address : addresses) {
      addressDetailList.add(populateAddressDetail(address));
    }

    // Step 3: Print address detail object list
    JsonElement results = gson.toJsonTree(addressDetailList);
    System.out.println(results);
  }

  /**
   * @param address
   * @return
   */
  private static AddressDetail populateAddressDetail(String address) {
    AddressDetail addressDetail = new AddressDetail(address, new GoogleAPILocationExtractor());
    for (int i = 0; i < MAX_ATTEMPTS; i++) {
      logger.logp(Level.SEVERE, MainApp.class.getName(), "populateAddressDetail",
          String.format("Attempt to find location for address '%s': #%d", address, i + 1));
      if (LocationStatus.NOT_FOUND.equals(addressDetail.getStatus())) {
        addressDetail.getLocation();
      }

      if (LocationStatus.FOUND.equals(addressDetail.getStatus())) {
        logger.logp(Level.FINEST, MainApp.class.getName(), "populateAddressDetail",
            String.format("Found location for Address: %s", address));
        break;
      }
    }
    return addressDetail;
  }
}
