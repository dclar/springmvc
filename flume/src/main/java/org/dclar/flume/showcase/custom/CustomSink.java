package org.dclar.flume.showcase.custom;

import org.apache.flume.Channel;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.sink.AbstractSink;

public class CustomSink extends AbstractSink {

    @Override
    public Status process() throws EventDeliveryException {

        Channel c = getChannel();
        Transaction transaction = c.getTransaction();
        try {
            transaction.begin();
            Event e = c.take();
            if (e != null) {
                String str = new String(e.getBody());
                System.out.println(str);
            }
            transaction.commit();
            return Status.READY;
        } catch (Exception ee) {
            transaction.rollback();
        } finally {
            transaction.close();
        }
        return Status.BACKOFF;
    }
}
