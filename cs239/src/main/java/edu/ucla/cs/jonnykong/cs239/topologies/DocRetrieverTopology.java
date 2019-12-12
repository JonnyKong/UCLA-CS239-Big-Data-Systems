package edu.ucla.cs.jonnykong.cs239.topologies;

import edu.ucla.cs.jonnykong.cs239.SimpleQueryFilterCreator;
import edu.ucla.cs.jonnykong.cs239.bolt.CouchLookupBolt;
import edu.ucla.cs.jonnykong.cs239.mapper.CouchLookupMapper;
import edu.ucla.cs.jonnykong.cs239.topologies.DocRetrieverSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class DocRetrieverTopology {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("doc-retriever", new DocRetrieverSpout());

        CouchLookupBolt couchLookupBolt =
                new CouchLookupBolt("http://127.0.0.1:5984", "baseball", new SimpleQueryFilterCreator(), new CouchLookupMapper("_id", "Content"));
        builder.setBolt("couch-retriever", couchLookupBolt).shuffleGrouping("doc-retriever");

        Config conf = new Config();
        conf.setDebug(true);

        LocalCluster localCluster = new LocalCluster();
        try {
            localCluster.submitTopology("doc-retriever-topology", conf, builder.createTopology());
            Thread.sleep(10000);
            localCluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            localCluster.shutdown();
        }
    }
}
