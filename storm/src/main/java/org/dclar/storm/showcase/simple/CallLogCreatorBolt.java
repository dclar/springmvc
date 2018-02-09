package org.dclar.storm.showcase.simple;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.dclar.storm.showcase.util.MyUtil;

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
        System.out.println("Bout MyBolt : prepare()");
        MyUtil.log(this, "Bout MyBolt : prepare()");
    }

    /**
     * 每一个元祖都交给execute执行
     *
     * @param tuple
     */
    @Override
    public void execute(Tuple tuple) {

        // 接收电话主叫
        // String from = tuple.getString(0);
        // 获得zk托管的idx
        int index = tuple.getInteger(0);

        // 接收电话主叫
        String from = tuple.getString(1);

        // 接收被叫
        // String to = tuple.getString(1);
        String to = tuple.getString(2);
        //Integer duration = tuple.getInteger(2);
        Integer duration = tuple.getInteger(3);

        // 进行了format处理
        // collector.emit(new Values(from + " - " + to, duration));
        collector.emit(new Values(index, from + " - " + to, duration));

        System.out.println("Bout MyBolt : execute() " + from + "-" + to + "-" + duration);
        // MyUtil.log(this, "Bout MyBolt : execute() " + from + "-" + to + "-" + duration);
        MyUtil.log(this, "Bout MyBolt : execute() " + from + "-" + to + "-" + duration + "----------------> " + index + ": " + from);
    }

    @Override
    public void cleanup() {
        System.out.println("Bout MyBolt : cleanup()");
    }

    /**
     * 继续声明输出的schema
     *
     * @param declarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //declarer.declare(new Fields("call", "duration"));
        declarer.declare(new Fields("index", "call", "duration"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
