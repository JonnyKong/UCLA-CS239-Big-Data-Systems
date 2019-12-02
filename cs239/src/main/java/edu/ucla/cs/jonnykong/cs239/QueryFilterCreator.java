package edu.ucla.cs.jonnykong.cs239;

import org.apache.storm.tuple.ITuple;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface QueryFilterCreator extends Serializable {
    /**
     * Create a query Filter by given Tuple.
     *
     * @param map key-value pairs
     * @return query Filter
     */
    JSONObject createFilter(Map<String, String> map);

    /**
     * Create a query Filter by given trident keys.
     *
     * @param keys keys
     * @return query Filter
     */
    JSONObject createFilterByKeys(List<Object> keys);
}
