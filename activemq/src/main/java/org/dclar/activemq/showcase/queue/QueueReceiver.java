package org.dclar.activemq.showcase.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Enumeration;

public class QueueReceiver {

    public static void main(String[] args) throws JMSException {

        ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = cf.createConnection();
        connection.start();

        Enumeration names = connection.getMetaData().getJMSXPropertyNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            System.out.println("jms name === " + name);
        }

        final Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue("my-queue");
        MessageConsumer consumer = session.createConsumer(destination);

        int i = 0;

        while (i < 3) {
            {
                MapMessage message = (MapMessage) consumer.receive();

                System.out.println("收到消息: " + message.getString("message---" + i) + ", property = " +
                        message.getStringProperty("extra" + i));
                i++;
            }
        }

        session.close();
        connection.close();
    }
}
