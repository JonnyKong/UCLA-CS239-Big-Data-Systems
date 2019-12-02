package edu.ucla.cs.jonnykong.cs239;    // TODO: move to storm package

import java.io.Serializable;
import java.util.List;

import javafx.util.Pair;
import org.apache.storm.tuple.ITuple;
import org.apache.storm.tuple.Fields;

import edu.ucla.cs.jonnykong.cs239.CouchUtils;

/**
 * Given a Tuple/trident keys, serialize it.
 * TODO: This class may not be necessary for CouchDB.
 */


public class CouchInsertMapper implements Serializable {

    public CouchInsertMapper() {}

    /**
     * Map a tuple into JSON document.
     * @param tuple Input tuple
     * @return String, whose value is a JSON string.
     */
    String toDocument(ITuple tuple) {
        return tuple.getString(0);
    }
}