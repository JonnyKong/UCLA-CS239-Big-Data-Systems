package edu.ucla.cs.jonnykong.cs239.bolt; // TODO: move to storm package

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
import edu.ucla.cs.jonnykong.cs239.mapper.CouchDeleteMapper;


/**
 * Basic bolt for updating CouchDB
 */
public class CouchDeleteBolt implements IRichBolt {

    private CouchDbClient client;
    private final String host_addr;
    private final String db_name;
    private final CouchDeleteMapper mapper;

    public CouchDeleteBolt(String host_addr, String db_name,
                           CouchDeleteMapper mapper) {
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
            Pair<String, String> docid_rev = this.mapper.toDeleteTuple(tuple);
            if (docid_rev == null)
                return;
            String docid = docid_rev.getKey();
            String rev = docid_rev.getValue();
            
            this.client.delete(docid, rev);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void cleanup() {
        // TODO: close connection
        System.out.println("CouchDeleteBolt cleanup()");
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