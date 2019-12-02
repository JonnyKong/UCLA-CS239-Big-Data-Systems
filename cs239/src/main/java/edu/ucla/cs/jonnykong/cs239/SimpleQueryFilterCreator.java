package edu.ucla.cs.jonnykong.cs239;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class SimpleQueryFilterCreator implements QueryFilterCreator {
    /**
     * @param map key-value pairs
     * @return JSONObject
     * sample filter:
     * {
     *     "selector": {
     *         "year": {"$eq": 2010}
     *     },
     *     "fields": ["_id", "_rev", "year", "title"],
     *     "sort": [{"year": "asc"}],
     *     "limit": 2,
     *     "skip": 0,
     *     "execution_stats": true
     * }
     */
    @Override
    public JSONObject createFilter(Map<String, String> map) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(jsonString == null) return null;

        return new JSONObject(jsonString);
    }

    @Override
    public JSONObject createFilterByKeys(List<Object> keys) {
        return null;
    }
}
