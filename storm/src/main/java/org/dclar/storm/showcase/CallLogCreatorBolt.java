package org.dclar.storm.showcase;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * Bolt
 * 接收Spout的数据并进行format处理
 */
public class CallLogCreatorBolt implements IRichBolt {
    //Create instance for OutputCollector which collects and emits tuples to produce output
    private OutputCollector collector;

    /**
     * 初始化
     *
     * @param conf
     * @param context
     * @param collector
     */
    @Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        System.out.println("Bout CallLogCreatorBolt : prepare()");
    }

    /**
     * 每一个元祖都交给execute执行
     *
     * @param tuple
     */
    @Override
    public void execute(Tuple tuple) {

        // 接收电话主叫
        String from = tuple.getString(0);

        // 接收被叫
        String to = tuple.getString(1);
        Integer duration = tuple.getInteger(2);

        // 进行了format处理
        collector.emit(new Values(from + " - " + to, duration));
        System.out.println("Bout CallLogCreatorBolt : execute() " + from + "-" + to + "-" + duration);
    }

    @Override
    public void cleanup() {
        System.out.println("Bout CallLogCreatorBolt : cleanup()");
    }

    /**
     * 继续声明输出的schema
     *
     * @param declarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("call", "duration"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
