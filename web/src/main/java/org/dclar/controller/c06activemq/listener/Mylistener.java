package org.dclar.controller.c06activemq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 使用监听器实现消费消息
 *
 */
public class Mylistener implements MessageListener {

    @Override
    public void onMessage(Message message) {

        TextMessage msg = (TextMessage) message;
        try {
            System.out.println("Receive Listener msg === " + msg.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
