package edu.ucla.cs.jonnykong.cs239; // TODO: move to storm package

import java.util.Map;
import java.util.function.Function;
import javafx.util.Pair;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;

import edu.ucla.cs.jonnykong.cs239.CouchDbClient;
import edu.ucla.cs.jonnykong.cs239.CouchInsertMapper;
import edu.ucla.cs.jonnykong.cs239.CouchUtils;


/**
 * Basic bolt for inserting CouchDB
 */
public class CouchInsertBolt implements IRichBolt {

    private CouchDbClient client;
    private final String host_addr;
    private final String db_name;
    private final CouchInsertMapper mapper;

    public CouchInsertBolt(String host_addr, String db_name, CouchInsertMapper mapper) {
        this.host_addr = host_addr;
        this.db_name = db_name;
        this.mapper = mapper;
    }

    @Override
    public void prepare(Map<String, Object> stormConf, TopologyContext context,
            OutputCollector collector) {
        this.client = new CouchDbClient(this.host_addr, this.db_name);
    }

    @Override
    public void execute(Tuple tuple) {
        try {
            String doc = this.mapper.toDocument(tuple);
            this.client.insert(doc);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void cleanup() {
        // TODO: close connection
        System.out.println("CouchInsertBolt cleanup()");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // No output
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }
}