package org.dclar.activemq.showcase.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueSender {

    public static void main(String[] args) throws JMSException {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);

        Destination destination = session.createQueue("my-queue");

        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 3; i++) {

            MapMessage message = session.createMapMessage();

            message.setStringProperty("extra" + i, "ok");

            message.setString("message---" + i, "my map message " + i);

            producer.send(message);
        }

        session.commit();
        session.close();
        connection.close();

    }
}
