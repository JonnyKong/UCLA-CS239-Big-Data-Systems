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
import edu.ucla.cs.jonnykong.cs239.CouchUtils;
import edu.ucla.cs.jonnykong.cs239.CouchUpdateMapper;


/**
 * Basic bolt for updating CouchDB
 */
public class CouchUpdateBolt implements IRichBolt {

    private CouchDbClient client;
    private final String host_addr;
    private final String db_name;
    private final CouchUpdateMapper mapper;

    public CouchUpdateBolt(String host_addr, String db_name,
                           CouchUpdateMapper mapper) {
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
            Pair<String, String> docid_value = this.mapper.toUpdateTuple(tuple);
            if (docid_value == null)
                return;
            String docid = docid_value.getKey();
            String value = docid_value.getValue();
            
            this.client.update(docid, value);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void cleanup() {
        // TODO: close connection
        System.out.println("CouchUpdateBolt cleanup()");
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