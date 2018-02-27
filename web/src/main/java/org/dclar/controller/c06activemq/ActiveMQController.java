package org.dclar.controller.c06activemq;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.TextMessage;

@RestController()
@RequestMapping("/activemq")
public class ActiveMQController {

    //@Autowired
    private JmsTemplate jt;

    @RequestMapping(value = "/queue/send")
    public void send() {
        jt.send(session -> {
            TextMessage message = session.createTextMessage("Spring msg===");
            return message;
        });
    }

    /**
     * 不建议使用receive的方法来实现消息读取
     * 因为此方法会阻塞线程调用,建议使用listener的方式
     * 参看{@link org.dclar.controller.c06activemq.listener.Mylistener}
     */
    @RequestMapping(value = "/queue/receive")
    public void receive() {

        // 阻塞取得数据
        String msg = (String) jt.receiveAndConvert();
        System.out.println("spring msg is === " + msg);

    }

    @RequestMapping(value = "/topic/send")
    public void sendTopic() {

        // 此处在spring中配置了 这里直接声明也可以
        /*
          activemq.xml中配置

         */
        jt.setDefaultDestination(new ActiveMQTopic("spring-topic"));
        jt.send(session -> {
            TextMessage message = session.createTextMessage("Spring topic msg===");
            return message;
        });
    }

    /**
     * 不建议使用receive的方法来实现消息读取
     * 因为此方法会阻塞线程调用,建议使用listener的方式
     * 参看{@link org.dclar.controller.c06activemq.listener.Mylistener}
     */
    @Deprecated
    @RequestMapping(value = "/topic/receive")
    public void receiveTopic() {

        // 这里会阻塞取得mq的数据
        String msg = (String) jt.receiveAndConvert();
        System.out.println("spring topic msg is === " + msg);
    }

}
