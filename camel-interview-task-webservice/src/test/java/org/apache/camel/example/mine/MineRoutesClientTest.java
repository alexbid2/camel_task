package org.apache.camel.example.mine;

import org.apache.camel.test.AvailablePortFinder;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Unit test of our routes
 */
public class MineRoutesClientTest extends CamelSpringTestSupport {

    // should be the same address as we have in our route
    public static final String URL = "http://localhost:%s/webservices/mine";
    public static final int PORT = AvailablePortFinder.getNextAvailable();
    
    @BeforeClass
    public static void setUpBeforeClass() {
        System.setProperty("port", String.valueOf(PORT));
    }
    
    @AfterClass
    public static void tearDownBeforeClass() {
        System.clearProperty("port");
    }

    protected static MineEndpoint createCXFClient(String url) {
        // we use CXF to create a client for us as its easier than JAXWS and works
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(MyEndpointService.class);
        factory.setAddress(url);
        return (MineEndpoint) factory.create();
    }

	@Test
	public void testMyService() throws Exception {
		// create input parameter
		InputMyInput input = new InputMyInput();
		input.setMyBody("My Body");
		// create the webservice client and send the request
		MineEndpoint client = createCXFClient(String.format(URL, PORT));
		OutputMyOutput out = client.myOperation(input);

		// assert we got a OK back
		assertEquals("OK", out.getCode());

	}
    
    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext(new String[] {"camel-context.xml"});
    }
}