package com.shopizer.modules.shipping.distance.test;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.salesmanager.core.business.common.model.Delivery;
import com.salesmanager.core.business.reference.country.model.Country;
import com.salesmanager.core.business.reference.zone.model.Zone;
import com.salesmanager.core.business.shipping.model.ShippingOrigin;
import com.salesmanager.core.business.shipping.model.ShippingQuote;
import com.salesmanager.core.modules.constants.Constants;
import com.salesmanager.core.modules.integration.shipping.model.ShippingQuotePrePostProcessModule;

@ContextConfiguration(locations = {
		"classpath:spring/spring-context-test.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class
})
public class ShippingDistanceProcessorTestCase {
	
	@Autowired
	ShippingQuotePrePostProcessModule shippingDecisionTablePreProcessor;

	@Test
	public void testDistance() throws Exception {
		
		ShippingQuote shippingQuote = new ShippingQuote();
		
		Delivery delivery = new Delivery();
		delivery.setAddress("358 Du Languedoc");
		delivery.setCity("Boucherville");
		delivery.setPostalCode("J4B 8J9");
		
		Country country = new Country();
		country.setIsoCode("CA");
		country.setName("Canada");
		delivery.setCountry(country);
		
		Zone zone = new Zone();
		zone.setCode("QC");
		zone.setName("Quebec");
		
		delivery.setZone(zone);
		
		ShippingOrigin origin = new ShippingOrigin();
		origin.setAddress("7070, avenue Henri-Julien");
		origin.setCity("Montreal");
		origin.setPostalCode("H2S 3S3");
		origin.setZone(zone);
		origin.setCountry(country);
		
		shippingDecisionTablePreProcessor.prePostProcessShippingQuotes(shippingQuote, null, null, delivery, origin, null, null, null, null, null, Locale.CANADA);
		
		System.out.println(delivery.getLatitude());
		System.out.println(delivery.getLongitude());
		System.out.println(shippingQuote.getQuoteInformations().get(Constants.DISTANCE_KEY));

	}
}
