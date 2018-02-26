package org.dclar.activemq.showcase.broker;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import java.net.URI;
import java.net.URISyntaxException;

public class FactoryBroker {

    public static void main(String[] args) throws Exception {

        String uri = "properties:broker.properties";
        BrokerService brokerService = BrokerFactory.createBroker(new URI(uri));
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();

    }
}
