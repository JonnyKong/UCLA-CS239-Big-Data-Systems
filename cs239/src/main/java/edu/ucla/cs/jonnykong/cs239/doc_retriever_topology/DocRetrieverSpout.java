package edu.ucla.cs.jonnykong.cs239.doc_retriever_topology;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class DocRetrieverSpout implements IRichSpout {

    private static final long serialVersionUID = 441966625018584917L;
    private SpoutOutputCollector collector;
    private String[] docIds = {
            "0aca5f3e1ce7e1fd1bda272a23000b06",
            "0aca5f3e1ce7e1fd1bda272a23001426",
            "0aca5f3e1ce7e1fd1bda272a23001e49"
    };
    private boolean isComplete;

    @Override
    public void open(Map<String, Object> map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    @Override
    public void close() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void nextTuple() {
        if(!isComplete) {
            System.out.println("Doc Retriever Spout");
            for(String id: docIds) {
                this.collector.emit(new Values(id));
            }
            isComplete = true;
        } else {
            this.close();
        }
    }

    @Override
    public void ack(Object o) {

    }

    @Override
    public void fail(Object o) {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("Content"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
