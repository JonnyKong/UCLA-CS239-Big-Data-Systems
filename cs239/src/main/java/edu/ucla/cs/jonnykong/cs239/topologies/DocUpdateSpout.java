package edu.ucla.cs.jonnykong.cs239.topologies;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;


public class DocUpdateSpout implements IRichSpout {
    /**
     * 
     */
    private static final long serialVersionUID = 441966625018520917L;
    private SpoutOutputCollector collector;
    private String[] sentences = {
            "{\"_id\":\"0aca5f3e1ce7e1fd1bda272a23000b06\",\"_rev\":\"2-c8e2c543280132481424e37aa29df77b\",\"key\":\"new_value\"}"
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
