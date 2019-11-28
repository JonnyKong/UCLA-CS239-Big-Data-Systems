package edu.ucla.cs.jonnykong.cs239;    // TODO: move to storm package

import java.util.Map;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;


/**
 * Basic bolt for updating CouchDB
 */
public class CouchUpdateBolt extends BaseRichBolt {

    private String url;
    private CouchMapper mapper;

    /**
     * CouchUpdateBolt constructor.
     * @paral url The url to a CouchDB instance
     */
    public CouchUpdateBolt(String url, CouchMapper mapper) {
        this.url = url;
        this.mapper = mapper;
    }

    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context,
            OutputCollector collector) {

    }

    @Override
    public void execute(Tuple tuple) {
        
    }

    @Override
    public void cleanup() {
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        
    }
}