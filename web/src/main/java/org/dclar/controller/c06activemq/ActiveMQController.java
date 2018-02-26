package org.dclar.controller.c06activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.TextMessage;

@RestController()
@RequestMapping("/activemq")
public class ActiveMQController {

    @Autowired
    private JmsTemplate jt;

    @RequestMapping(value = "/send")
    public void send() {
        jt.send(session -> {
            TextMessage message = session.createTextMessage("Spring msg===");
            return message;
        });
    }

    @RequestMapping(value = "/receive")
    public void receive() {
        String msg = (String) jt.receiveAndConvert();
        System.out.println("spring msg is === " + msg);

    }
}
