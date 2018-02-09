package org.dclar.storm.showcase.reliable;

import org.apache.storm.shade.com.google.common.annotations.VisibleForTesting;
import org.apache.storm.shade.org.apache.zookeeper.WatchedEvent;
import org.apache.storm.shade.org.apache.zookeeper.Watcher;
import org.apache.storm.shade.org.apache.zookeeper.ZooKeeper;
import org.apache.storm.shade.org.apache.zookeeper.data.Stat;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.dclar.storm.showcase.util.MyUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.*;


/**
 *
 */
public class MySpout implements IRichSpout {

    //Create instance for SpoutOutputCollector which passes tuples to bolt.
    private SpoutOutputCollector collector;
    private boolean completed = false;

    //Create instance for TopologyContext which contains topology data.
    private TopologyContext context;

    //Create instance for Random class.
    private Random randomGenerator = new Random();

    // 使用zookeeper托管idx后,成员变量废弃
    // private Integer idx = 0;

    private int index = 0;

    private List<Integer> ids;

    private Map<Integer, String> toSend = new HashMap<>();

    private Map<Integer, String> allMessage = new HashMap<>();

    // 失败消息 id + 次数
    private Map<Integer, Integer> failMessage = new HashMap<>();

    // 最大的重试次数
    private static int MAX_RETRY = 5;


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

        for (int i = 0; i < 10; i++) {
            // 初始化消息集合
            toSend.put(i, "" + i + ",tom" + i + "," + (10 + i));
            allMessage.put(i, "" + i + ",tom" + i + "," + (10 + i));
        }
    }


    private boolean over;

    /**
     */
    @Override
    public void nextTuple() {

        if (toSend != null && !toSend.isEmpty()) {
            for (Map.Entry<Integer, String> entry : toSend.entrySet()) {

                // 发射内容 + 发射msgId
                collector.emit(
                        new Values(entry.getValue()), // 内容
                        entry.getKey());    // 消息id
            }

            // 调用完毕后直接清除
            toSend.clear();
            over = true;
        }
    }

    /**
     * 声明tuple输出的schema
     *
     * @param declarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // idx托管给zookeeper后,声明的schema中加入index
        // declarer.declare(new Fields("id", "name", "age"));
        declarer.declare(new Fields("message"));

    }

    /**
     * 生命周期最终 销毁
     */
    //Override all the interface methods
    @Override
    public void close() {

        System.out.println("Spout.closed : closed!+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        MyUtil.log(this, "Spout.closed : closed!+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
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

        // 在bolt中ack tuple后 此处可以收到msgId
        Integer index = (Integer)msgId;
        // 发送队列删除
        toSend.remove(index);
        // 失败队列删除
        failMessage.remove(index);
    }

    /**
     * 指定tuple未处理
     *
     * @param msgId
     */
    @Override
    public void fail(Object msgId) {

        Integer index = (Integer) msgId;

        Integer count = failMessage.get(index);

        count = count == null ? 0 : count;

        // 判断消息失败额次数
        if (count < MAX_RETRY) {
            failMessage.put(index, count + 1);
            toSend.put(index, allMessage.get(index));
        } else {
            // 到达最大重试值
            MyUtil.log(this, "msg: " + index + "has been reached max retries");
            toSend.remove(index);
        }
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

}
