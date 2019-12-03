package edu.ucla.cs.jonnykong.cs239.bolt;

import edu.ucla.cs.jonnykong.cs239.CouchDbClient;
import edu.ucla.cs.jonnykong.cs239.QueryFilterCreator;
import edu.ucla.cs.jonnykong.cs239.mapper.CouchLookupMapper;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouchLookupBolt implements IRichBolt {

    private final String host_addr;
    private final String db_name;
    private CouchDbClient couchDbClient;
    private CouchLookupMapper couchLookupMapper;
    private OutputCollector collector;
    private QueryFilterCreator queryCreator;

    public CouchLookupBolt(String host_addr, String db_name, QueryFilterCreator queryCreator, CouchLookupMapper couchLookupMapper) {
        this.host_addr = host_addr;
        this.db_name = db_name;
        this.couchLookupMapper = couchLookupMapper;
        this.queryCreator = queryCreator;
    }

    @Override
    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.couchDbClient = new CouchDbClient(this.host_addr, this.db_name);
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        if(tuple.size() == 1) {
            String doc = couchDbClient.lookup(tuple.getString(0));
            Values values = new Values();
            values.add(doc);
            this.collector.emit(tuple, values);
            return;
        }

        try {
            Map<String, String> map = new HashMap<>();
            for(int i = 0; i < tuple.size(); i += 2) {
                map.put(tuple.getString(i), tuple.getString(i + 1));
            }
            JSONObject filter = queryCreator.createFilter(map);
            JSONArray docs = couchDbClient.lookupWithFields(filter).getJSONArray("docs");

            List<Values> valuesList = new ArrayList<>();
            for(int i = 0; i < docs.length(); i++) {
                valuesList.add(couchLookupMapper.toTuple(tuple, docs.getJSONObject(i)));
            }

            for (Values values: valuesList) {
                this.collector.emit(tuple, values);
            }
            this.collector.ack(tuple);
        } catch (Exception e) {
            this.collector.reportError(e);
            this.collector.fail(tuple);
        }
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
