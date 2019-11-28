package edu.ucla.cs.jonnykong.cs239;

import java.util.HashMap;
import java.util.Map;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;


public class WordCountBolt implements IRichBolt {
    /**
     * 
     */
    private static final long serialVersionUID = -4130092930769665618L;
    Map < String, Integer > counters;
    Integer id;
    String name;
    String fileName;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.counters = new HashMap < > ();
        this.name = context.getThisComponentId();
        this.id = context.getThisTaskId();
    }
    @Override
    public void execute(Tuple input) {
        String word = input.getStringByField("word");
        if (!counters.containsKey(word)) {
            counters.put(word, 1);
        } else {
            counters.put(word, counters.get(word) + 1);
        }
    }
    @Override
    public void cleanup() {
        System.out.println("Final word count:::::");
        for (Map.Entry < String, Integer > entry: counters.entrySet()) {
            System.out.println(entry.getKey() + "-" + entry.getValue());
        }
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO Auto-generated method stub
    }
    @Override
    public Map < String, Object > getComponentConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }
}
