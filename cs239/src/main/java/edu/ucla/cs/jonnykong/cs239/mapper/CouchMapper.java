package edu.ucla.cs.jonnykong.cs239;    // TODO: move to storm package

import java.io.Serializable;
import javafx.util.Pair;
import org.apache.storm.tuple.ITuple;

/**
 * Given a Tuple/trident keys, serialize it.
 * TODO: This class may not be necessary for CouchDB.
 */
public class CouchMapper implements Serializable {

    /**
     * Converts a tuple to a key-value pair.
     */
    Pair<String, String> toKeyValuePair(ITuple tuple) {
        String key, value;
        try {
            key = tuple.getValueByField("key").toString();
            value = tuple.getValueByField("value").toString();
        } catch (IllegalAccessError e) {
            System.out.println(e);
            return null;
        } 
        return new Pair<String, String>(key, value);
    }
}