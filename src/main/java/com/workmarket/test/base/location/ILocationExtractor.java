/**
 * 
 */
package com.workmarket.test.base.location;

import com.workmarket.test.base.address.AddressDetail;

/**
 * @author kushh
 *
 */
public interface ILocationExtractor {

	public Location extractLocation(AddressDetail addressDetails);
}
