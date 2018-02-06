package org.dclar.storm.showcase;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * 实现了IRichSpout类
 * 随机生成1000个主叫被叫电话信息传递到Bolt中
 */
public class FakeCallLogReaderSpout implements IRichSpout {

    //Create instance for SpoutOutputCollector which passes tuples to bolt.
    private SpoutOutputCollector collector;
    private boolean completed = false;

    //Create instance for TopologyContext which contains topology data.
    private TopologyContext context;

    //Create instance for Random class.
    private Random randomGenerator = new Random();
    private Integer idx = 0;

    /**
     * 生命周期初始化
     *
     * @param conf
     * @param context
     * @param collector
     */
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.context = context;
        this.collector = collector;
        System.out.println("Spout.open : opened!+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    /**
     * 业务:模拟输出通话记录who 给 who打电话 打了多久
     * 通过collector输出数据
     */
    @Override
    public void nextTuple() {
        if (this.idx <= 1000) {

            // fake telephones
            List<String> mobileNumbers = new ArrayList<String>();
            mobileNumbers.add("1234123401");
            mobileNumbers.add("1234123402");
            mobileNumbers.add("1234123403");
            mobileNumbers.add("1234123404");

            Integer localIdx = 0;
            while (localIdx++ < 100 && this.idx++ < 1000) {
                String fromMobileNumber = mobileNumbers.get(randomGenerator.nextInt(4));
                String toMobileNumber = mobileNumbers.get(randomGenerator.nextInt(4));

                while (fromMobileNumber == toMobileNumber) {
                    toMobileNumber = mobileNumbers.get(randomGenerator.nextInt(4));
                }

                Integer duration = randomGenerator.nextInt(60);
                this.collector.emit(new Values(fromMobileNumber, toMobileNumber, duration));

                System.out.println("spout.emit : " + fromMobileNumber + ", " + toMobileNumber + ", " + duration);
            }
        }
    }

    /**
     * 声明tuple输出的schema
     *
     * @param declarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("from", "to", "duration"));
    }

    /**
     * 生命周期最终 销毁
     */
    //Override all the interface methods
    @Override
    public void close() {

        System.out.println("Spout.closed : closed!+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public boolean isDistributed() {
        return false;
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }

    /**
     * 确认特定的tuple被处理
     *
     * @param msgId
     */
    @Override
    public void ack(Object msgId) {
    }

    /**
     * 指定tuple未处理
     *
     * @param msgId
     */
    @Override
    public void fail(Object msgId) {
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
