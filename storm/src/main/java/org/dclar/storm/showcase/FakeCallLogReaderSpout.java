package org.dclar.storm.showcase;

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

    // 使用zookeeper托管idx后,成员变量废弃
    // private Integer idx = 0;

    private int index = 0;

    private List<Integer> ids;

//    public FakeCallLogReaderSpout() {
//
//
//    }

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

        ids = context.getComponentTasks("call-log-creator-boltl");

        System.out.println("Spout.open : opened!+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        MyUtil.log(this, "Spout.open : opened!+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    /**
     * 业务:模拟输出通话记录who 给 who打电话 打了多久
     * 通过collector输出数据
     */
    @Override
    public void nextTuple() {

        // int idx = getIndexFromZK();
        if (index < 10) {

            // fake telephones
            List<String> mobileNumbers = new ArrayList<String>();
            mobileNumbers.add("1234123401");
            mobileNumbers.add("1234123402");
            mobileNumbers.add("1234123403");
            mobileNumbers.add("1234123404");

            MyUtil.log(this, "prepared Telephones ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            //Integer localIdx = 0;

            // while (localIdx++ < 100 && idx++ < 1000) {
            // 目的为产生10条记录
            for (; index < 10; index++) {
                String fromMobileNumber = mobileNumbers.get(randomGenerator.nextInt(4));
                String toMobileNumber = mobileNumbers.get(randomGenerator.nextInt(4));

                while (fromMobileNumber == toMobileNumber) {
                    toMobileNumber = mobileNumbers.get(randomGenerator.nextInt(4));
                }

                Integer duration = randomGenerator.nextInt(60);
                // this.collector.emit(new Values(index, fromMobileNumber, toMobileNumber, duration));

                while (true) {
                    int id = ids.get(new Random().nextInt(3));
                    if (id % 2 == 0) {
                        this.collector.emitDirect(id, new Values(index, fromMobileNumber, toMobileNumber, duration));
                        break;
                    }
                }

                System.out.println("spout.emit : " + fromMobileNumber + ", " + toMobileNumber + ", " + duration);
                //MyUtil.log(this, "spout.emit : " + fromMobileNumber + ", " + toMobileNumber + ", " + duration);
                MyUtil.log(this, "spout.emit : " + fromMobileNumber + ", " + toMobileNumber + ", " + duration + "spout ------> " + index);
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
        // idx托管给zookeeper后,声明的schema中加入index
        declarer.declare(new Fields("index", "from", "to", "duration"));
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


    /**
     * 使用zk管理idx 每次娶到idx时自动 + 1操作 涉及到bytes与int的相互转换
     *
     * @return
     */
    public int getIndexFromZK() {

        // 由于zookeeper无法进行串行化的调用,每一次获取一个新的zookeeper来进行处理
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper("centos01:2181", 1000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Stat stat = new Stat();
            byte[] bytes = zooKeeper.getData("/index", null, stat);
            int i = MyUtil.bytes2int(bytes);
            zooKeeper.setData("/index", MyUtil.int2Bytes(i + 1), stat.getVersion());

            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


}
