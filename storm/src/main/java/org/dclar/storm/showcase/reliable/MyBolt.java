package org.dclar.storm.showcase.reliable;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.dclar.storm.showcase.util.MyUtil;

import java.util.Map;
import java.util.Random;

/**
 * Bolt
 * 接收Spout的数据并进行format处理
 */
public class MyBolt implements IRichBolt {
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

        String msg = tuple.getString(0);


        // 模拟偶尔出现失败的场景
        if (new Random().nextBoolean()) {
            // 通知OK
            MyUtil.log(this, msg + ": ack()");
            collector.ack(tuple);
        } else {
            // 通知失败
            MyUtil.log(this, msg + ": fail()");
            collector.fail(tuple);
        }


        MyUtil.log(this, msg);
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

        // 不声明也可以因为没有继续的bolt来执行
        declarer.declare(new Fields("id", "name", "age"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
