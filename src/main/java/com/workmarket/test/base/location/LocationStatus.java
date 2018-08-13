/**
 * 
 */
package com.workmarket.test.base.location;

/**
 * @author kushh
 *
 */
public enum LocationStatus {
	FOUND("FOUND"), 
	NOT_FOUND("NOT FOUND");

	private String displayText;

	/**
	 * @return the displayText
	 */
	public String getDisplayText() {
		return displayText;
	}

	/**
	 * @param displayText
	 *            the displayText to set
	 */
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	/**
	 * @param displayText
	 */
	private LocationStatus(String displayText) {
		this.displayText = displayText;
	}
}
