package edu.ucla.cs.jonnykong.cs239;    // TODO: move to storm package

import java.io.Serializable;
import javafx.util.Pair;
import org.apache.storm.tuple.ITuple;

import edu.ucla.cs.jonnykong.cs239.CouchUtils;

/**
 * Given a Tuple/trident keys, serialize it.
 * TODO: This class may not be necessary for CouchDB.
 */


public class CouchUpdateMapper implements Serializable {

    /**
     * TODO: how should the mapper be parameterized?
     */
    public CouchUpdateMapper() {}

    /**
     * Map a tuple into a <docid, value> pair for update. 
     * @param tuple Input tuple
     * @return <docid, value> pair, where value is a JSON string containing the "_rev" field. If a
     *  tuple doesn't have "_id" or "_rev" fields, return null.
     */
    Pair<String, String> toUpdateTuple(ITuple tuple) {
        // Make sure the tuple have required fields "id" and "_rev"
        String _id, _rev;
        String value = tuple.getString(0);
        try {
            _id = CouchUtils.getAttr(value, "_id");
            _rev = CouchUtils.getAttr(value, "_rev");
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return new Pair<String, String>(_id, value);
    }
}