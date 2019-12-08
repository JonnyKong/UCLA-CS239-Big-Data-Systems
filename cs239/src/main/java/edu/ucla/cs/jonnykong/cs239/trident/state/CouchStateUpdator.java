package edu.ucla.cs.jonnykong.cs239.trident.state;

import java.util.List;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.state.BaseStateUpdater;
import org.apache.storm.trident.tuple.TridentTuple;

public class MongoStateUpdater extends BaseStateUpdater<MongoState>  {

    @Override
    public void updateState(CouchState state, List<TridentTuple> tuples,
                            TridentCollector collector) {
        state.updateState(tuples, collector);
    }

}