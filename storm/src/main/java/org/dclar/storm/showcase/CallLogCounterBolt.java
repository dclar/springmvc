package org.dclar.storm.showcase;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.dclar.storm.showcase.util.MyUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Bolt
 */
public class CallLogCounterBolt implements IRichBolt {
    Map<String, Integer> counterMap;
    private OutputCollector collector;

    @Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        this.counterMap = new HashMap<String, Integer>();
        this.collector = collector;
    }

    @Override
    public void execute(Tuple tuple) {
        // from to
        // String call = tuple.getString(0);
        int index = tuple.getInteger(0);

        String call = tuple.getString(1);

        // 通话时长
        // Integer duration = tuple.getInteger(1);
        Integer duration = tuple.getInteger(2);

        // 进行通话计数
        if (!counterMap.containsKey(call)) {
            counterMap.put(call, 1);
        } else {
            Integer c = counterMap.get(call) + 1;
            counterMap.put(call, c);
        }

        System.out.println("Bout CallLogCounterBolt : execute() " + call + "-" + duration);
        // MyUtil.log(this, "Bout CallLogCounterBolt : execute() " + call + "-" + duration);
        MyUtil.log(this, "Bout CallLogCounterBolt : execute() =====================>  " + index);
        // 通知Spout已经处理完毕
        collector.ack(tuple);
    }

    @Override
    public void cleanup() {
        for (Map.Entry<String, Integer> entry : counterMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
            MyUtil.log(this, entry.getKey() + " : " + entry.getValue());
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("call"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

}
