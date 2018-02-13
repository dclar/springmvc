package org.dclar.flume.showcase.custom;

import org.apache.flume.Event;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义source,连续发送10W的event的source
 */
public class CustomeSource extends AbstractSource {


    public synchronized void start() {

        super.start();
        ChannelProcessor cp = this.getChannelProcessor();
        Map<String, String> map = new HashMap<>();

        map.put("owner", "dclar");
        map.put("date", "2018/2/13");
        Event e = null;

        for (int i = 0; i < 100000; i++) {
            e = new SimpleEvent();
            e.setBody(("tom" + i).getBytes());
            e.setHeaders(map);
        }
        cp.processEvent(e);
    }

}
