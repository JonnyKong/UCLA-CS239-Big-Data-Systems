package edu.ucla.cs.jonnykong.cs239;    // TODO: move to storm package

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
 * Basic client for CouchDB.
 * TODO: make ops async
 * TODO: escape string
 * TODO: destructor to close connection?
 */
public class CouchDbClient {
    
    private String host_addr;
    private String db_name;
    private HttpClient http_client;

    /**
     * @param host_addr address of the couchDB host
     * @param db_name name of the dadabase
     */
    public CouchDbClient(String host_addr, String db_name) {
        this.host_addr = host_addr;
        this.db_name = db_name;
        this.http_client = HttpClient.newHttpClient();
    }

    /**
     * Insert a key-value pair.
     * @param value The value ASCII string in JSON format.
     * @return The automatically-generated keyid in ASCII string.
     */
    public String insert(String value) {
        // POST /{db}
        // Content-Type: application/json
        String uri_str = this.host_addr + "/" + this.db_name;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(value))
                .header("Content-Type", "application/json")
                .uri(new URI(uri_str))
                .build();
            HttpResponse<String> response = 
                this.http_client.send(request, HttpResponse.BodyHandlers.ofString());
            return CouchUtils.getAttr(response.body(), "id");
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Update a key-value pair.
     * @param key The key ASCII string.
     * @param value The value ASCII string. When updating an existing document, the current document
     *      revision must be included in the document (i.e. the request body), otherwise a conflict
     *      will be created.
     */
    public void update(String key, String value) {
        // PUT /{db}/{docid}
        // Content-Type: application/json
        String uri_str = this.host_addr + "/" + this.db_name + "/" + key;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(value))
                .header("Content-Type", "application/json")
                .uri(new URI(uri_str))
                .build();
            HttpResponse<String> response = 
                this.http_client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Lookup a specific document by id.
     * @param key The key ASCII string.
     * @return The string representation of a document.
     */
    public String lookup(String key) {
        // GET /{db}/{docid}
        String uri_str = this.host_addr + "/" + this.db_name + "/" + key;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(uri_str))
                .build();
            HttpResponse<String> response = 
                this.http_client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Lookup documents by applying a filter.
     * @param filter The JSONObject.
     * @return The JSON representation of documents.
     */
    public JSONObject lookupWithFields(JSONObject filter) {
        // POST /{db}/_find
        String uri_str = this.host_addr + "/" + this.db_name + "/_find";

        System.out.println("filter: " + filter.toString());

        StringBuilder data = new StringBuilder("{");
        for(String key: filter.keySet()) {
            data.append(key).append(": ").append(filter.get(key)).append(",");
        }
        data.deleteCharAt(data.length() - 1);
        data.append("}");
        System.out.println(data.toString());
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(data.toString()))
                    .headers("Accept", "application/json", "Content-Type", "application/json")
                    .uri(new URI(uri_str))
                    .build();

            HttpResponse<String> response =
                    this.http_client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("response: " + response.body());
            return new JSONObject(response.body());
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Delete a key-value pair.
     * @param key The key ASCII string.
     * @param rev The rev ASCII string.
     */
    public void delete(String key, String rev) {
        // DELETE /{db}/{docid}
        String uri_str = this.host_addr + "/" + this.db_name + "/" + key;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(new URI(uri_str))
                .header("If-Match", rev)
                .build();
            HttpResponse<String> response = 
                this.http_client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Test connection.
     */
    public void test() {
        // GET /
        String uri_str = this.host_addr;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(uri_str))
                .build();
            HttpResponse<String> response = this.http_client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /**
     * For testing.
     */
    public static void main(String[] args) {
        String host_addr = "http://127.0.0.1:5984";
        String db_name = "baseball";
        CouchDbClient client = new CouchDbClient(host_addr, db_name);

        System.out.println("Testing connection ...");
        client.test();
        
        System.out.println("Testing insert ...");
        String keyid = client.insert("{\"foo1\": \"bar1\", \"foo2\": \"bar2\"}");
        System.out.println("Inserted keyid: " + keyid);

        System.out.println("Testing lookup ...");
        System.out.println(client.lookup(keyid));
        
        System.out.println("Testing update ...");
        String value = "{\"foo1\": \"bar1\", \"foo2\": \"bar3\"}";
        try {
            String ret = client.lookup(keyid);
            String prev_rev = CouchUtils.getAttr(ret, "_rev");
            client.update(keyid, CouchUtils.setAttr(value, "_rev", prev_rev));
            System.out.println(client.lookup(keyid));
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Testing delete ...");
        try{
            String ret = client.lookup(keyid);
            String prev_rev = CouchUtils.getAttr(ret, "_rev");
            client.delete(keyid, prev_rev);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}