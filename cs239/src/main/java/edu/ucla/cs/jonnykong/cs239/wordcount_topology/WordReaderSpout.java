package edu.ucla.cs.jonnykong.cs239;

import java.util.Map;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;


public class WordReaderSpout implements IRichSpout {
    /**
     * 
     */
    private static final long serialVersionUID = 441966625018520917L;
    private SpoutOutputCollector collector;
    private String[] sentences = {
        "Hello World",
        "Apache Storm",
        "Big Data",
        "Big Data",
        "Machine Learning",
        "Hello World",
        "World",
        "Hello"
    };
    boolean isCompleted;
    String fileName;
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }
    @Override
    public void close() {
        // TODO Auto-generated method stub
    }
    @Override
    public void activate() {
        // TODO Auto-generated method stub
    }
    @Override
    public void deactivate() {
        // TODO Auto-generated method stub
    }
    @Override
    public void nextTuple() {
        if (!isCompleted) {
            for (String sentence: sentences) {
                for (String word: sentence.split(" ")) {
                    this.collector.emit(new Values(word));
                }
            }
            isCompleted = true;
        } else {
            this.close();
        }
    }
    @Override
    public void ack(Object msgId) {
        // TODO Auto-generated method stub
    }
    @Override
    public void fail(Object msgId) {
        // TODO Auto-generated method stub
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
    @Override
    public Map < String, Object > getComponentConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }
}
