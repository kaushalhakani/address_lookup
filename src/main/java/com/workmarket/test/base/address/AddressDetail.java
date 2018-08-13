/**
 * 
 */
package com.workmarket.test.base.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.workmarket.test.base.location.ILocationExtractor;
import com.workmarket.test.base.location.Location;
import com.workmarket.test.base.location.LocationStatus;

/**
 * @author kushh
 *
 */
public class AddressDetail {
	@Expose
	@SerializedName("address")
	private final String address;

	@Expose
	@SerializedName("status")
	private LocationStatus status;

	@Expose
	@SerializedName("location")
	private Location location;

	private ILocationExtractor locationExtractor;

	/**
	 * @param address
	 * @param googleAPILocationExtractor
	 */
	public AddressDetail(String address, ILocationExtractor locationExtractor) {
		super();
		this.address = address;
		this.status = LocationStatus.NOT_FOUND;
		this.locationExtractor = locationExtractor;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the status
	 */
	public LocationStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(LocationStatus status) {
		this.status = status;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		if (LocationStatus.NOT_FOUND.equals(getStatus())) {
			Location location = locationExtractor.extractLocation(this);
			setLocation(location);
		}
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
		if (location != null)
			setStatus(LocationStatus.FOUND);
	}
}
