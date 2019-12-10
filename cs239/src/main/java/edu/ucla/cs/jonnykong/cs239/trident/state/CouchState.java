package edu.ucla.cs.jonnykong.cs239.trident.state;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import edu.ucla.cs.jonnykong.cs239.CouchDbClient;
import edu.ucla.cs.jonnykong.cs239.QueryFilterCreator;
import edu.ucla.cs.jonnykong.cs239.mapper.CouchInsertMapper;
import edu.ucla.cs.jonnykong.cs239.mapper.CouchLookupMapper;
import org.apache.commons.lang.Validate;

import org.apache.storm.topology.FailedException;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.state.State;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CouchState implements State {

    private static final Logger LOG = LoggerFactory.getLogger(CouchState.class);

    private Options options;
    private CouchDbClient couchClient;
    private Map<String, Object> map;

    protected CouchState(Map<String, Object> map, Options options) {
        this.options = options;
        this.map = map;
    }

    public static class Options implements Serializable {
        private String url;
        private String collectionName;
        private CouchLookupMapper lookupMapper;
        private CouchInsertMapper mapper;
        private QueryFilterCreator queryCreator;

        public Options withUrl(String url) {
            this.url = url;
            return this;
        }

        public Options withCollectionName(String collectionName) {
            this.collectionName = collectionName;
            return this;
        }

        public Options withMapper(CouchInsertMapper mapper) {
            this.mapper = mapper;
            return this;
        }

        public Options withCouchLookupMapper(CouchLookupMapper lookupMapper) {
            this.lookupMapper = lookupMapper;
            return this;
        }

        public Options withQueryFilterCreator(QueryFilterCreator queryCreator) {
            this.queryCreator = queryCreator;
            return this;
        }
    }

    protected void prepare() {
        Validate.notEmpty(options.url, "url can not be blank or null");
        Validate.notEmpty(options.collectionName, "collectionName can not be blank or null");

        this.couchClient = new CouchDbClient(options.url, options.collectionName);
    }

    @Override
    public void beginCommit(Long txid) {
        LOG.debug("beginCommit is noop.");
    }

    @Override
    public void commit(Long txid) {
        LOG.debug("commit is noop.");
    }

    /**
     * Update Couch state.
     * @param tuples trident tuples
     * @param collector trident collector
     */
    public void updateState(List<TridentTuple> tuples, TridentCollector collector) {
        for (TridentTuple tuple : tuples) {
            String document = options.mapper.toDocument(tuple);
            try {
                this.couchClient.insert(document);
            } catch (Exception e) {
                LOG.warn("Batch write failed but some requests might have succeeded. Triggering replay.", e);
                throw new FailedException(e);
            }
        }

    }
}
