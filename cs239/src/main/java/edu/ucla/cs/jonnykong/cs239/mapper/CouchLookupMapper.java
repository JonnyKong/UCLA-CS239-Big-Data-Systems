package edu.ucla.cs.jonnykong.cs239.mapper;

import org.apache.storm.tuple.ITuple;
import org.apache.storm.tuple.Values;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CouchLookupMapper implements Serializable {

    private String[] fields;

    public CouchLookupMapper(String... fields) {
        this.fields = fields;
    }

    public Values toTuple(ITuple input, JSONObject doc) {
        Values values = new Values();

        for (String field: fields) {
            if (input.contains(field)) {
                values.add(input.getValueByField(field));
            } else {
                values.add(doc.get(field));
            }
        }

        return values;
    }
}
