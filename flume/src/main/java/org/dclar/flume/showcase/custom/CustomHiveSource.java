package org.dclar.flume.showcase.custom;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.FlumeException;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractEventDrivenSource;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义source,连续发送10W的event的source
 */
public class CustomHiveSource extends AbstractEventDrivenSource {


    @Override
    protected void doConfigure(Context context) throws FlumeException {

    }

    @Override
    protected void doStart() throws FlumeException {

        ChannelProcessor cp = this.getChannelProcessor();
        Map<String, String> map = new HashMap<>();

        map.put("country", "China");
        map.put("timestamp", "11:54:34 AM, June 12, 2012");
        Event e1 = new SimpleEvent();
        e1.setHeaders(map);
        e1.setBody("1    helloworld".getBytes());
        cp.processEvent(e1);

        Event e2 = new SimpleEvent();
        e2.setHeaders(map);
        e2.setBody("2,helloworld".getBytes());
        cp.processEvent(e2);

        Event e3 = new SimpleEvent();
        e3.setHeaders(map);
        e3.setBody("3 helloworld".getBytes());
        cp.processEvent(e3);


    }

    @Override
    protected void doStop() throws FlumeException {

    }

}
