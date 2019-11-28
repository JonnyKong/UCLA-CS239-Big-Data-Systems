package edu.ucla.cs.jonnykong.cs239;    // TODO: move to storm package

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject; 


/**
 * Common utils for CouchDB.
 */
public final class CouchUtils {
    /**
     * Get the specified attribute of a JSON string.
     * @param json_str The ASCII string in JSON format.
     * @param attr The specified attribute.
     * @return The value of the given attribute.
     * @throws org.json.simple.parser.ParseException
     */
    public static String getAttr(String json_str, String attr_key) 
            throws org.json.simple.parser.ParseException {
        Object obj = new JSONParser().parse(json_str);
        JSONObject jo = (JSONObject)obj;
        String keyid = (String)jo.get(attr_key);
        return keyid;
    }

    /**
     * Add or set the specified attribute of a JSON string.
     * @param json_str The value ASCII string in JSON format.
     * @param attr The specified attribute.
     * @return The updated JSON string.
     * @throws org.json.simple.parser.ParseException
     */
    public static String setAttr(String json_str, String attr_key, String attr_value) 
            throws org.json.simple.parser.ParseException {
        Object obj = new JSONParser().parse(json_str);
        JSONObject jo = (JSONObject)obj;
        jo.put(attr_key, attr_value);
        return jo.toJSONString();
    }
}