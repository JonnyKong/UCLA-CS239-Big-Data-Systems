package edu.ucla.cs.jonnykong.cs239.trident.state;

import java.util.Map;

import org.apache.storm.task.IMetricsContext;
import org.apache.storm.trident.state.State;
import org.apache.storm.trident.state.StateFactory;

public class CouchStateFactory implements StateFactory {

    private CouchState.Options options;

    public CouchStateFactory(CouchState.Options options) {
        this.options = options;
    }

    @Override
    public State makeState(Map<String, Object> conf, IMetricsContext metrics,
                           int partitionIndex, int numPartitions) {
        CouchState state = new CouchState(conf, options);
        state.prepare();
        return state;
    }

}
