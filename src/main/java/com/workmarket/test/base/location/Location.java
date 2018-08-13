/**
 * 
 */
package com.workmarket.test.base.location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author kushh
 *
 */
public class Location {
	@Expose
	@SerializedName("lat")
	private final double latitude;

	@Expose
	@SerializedName("lng")
	private final double longitude;

	/**
	 * @param latitude
	 * @param longitude
	 */
	public Location(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
}
