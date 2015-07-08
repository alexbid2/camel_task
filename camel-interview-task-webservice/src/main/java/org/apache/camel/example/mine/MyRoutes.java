package org.apache.camel.example.mine;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class MyRoutes extends RouteBuilder {

	public void configure() throws Exception {
		// webservice responses
		OutputMyOutput ok = new OutputMyOutput();
		ok.setCode("OK");

		from(
				"activemq:queue:input.queue?disableReplyTo=true")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						String theHeader = (String) exchange.getIn().getHeader(
								"MyAction");
						InputMyInput input = new InputMyInput();
						input.setMyBody(exchange.getIn().getBody(String.class));

						exchange.getOut().setBody(input);
						exchange.getOut().setHeader("MyAction", theHeader);
					}
				}).to("cxf:bean:my_service");

		from("cxf:bean:my_service")
				.convertBodyTo(InputMyInput.class)
				.setHeader(Exchange.FILE_NAME,
						constant("request-${date:now:yyyy-MM-dd-HHmmssSSS}"))
				.wireTap("file:///C:/8/").transform(constant(ok));

	}
}